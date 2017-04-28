package com.gcl.library.service;

import com.gcl.library.bean.*;
import com.gcl.library.repository.*;
import com.gcl.library.util.PinyinUtils;
import com.gcl.ml.bean.UserVector;
import com.gcl.ml.core.KNN;
import com.gcl.ml.util.MyVector;
import org.apache.commons.httpclient.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by gcl on 2016/12/18.
 */

@Component
@EnableScheduling
public class BatchService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BorrowedWatcherRepository borrowedWatcherRepository;

    private BookService bookService = new BookService();

    {
        // 初始化bookService
        bookService.setClient(new HttpClient());
    }

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private KeywordsRepository keywordsRepository;

    @Autowired
    private UserLabelRepository userLabelRepository;

    @Autowired
    private UserRatingRepository userRatingRepository;

    @Autowired
    private UserBookRepository userBookRepository;

    @Autowired
    private MLRecommendResository mlRecommendResository;

    @Autowired
    private JavaMailSender javaMailSender;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    private List<UserVector> userVectorList;

    /**
     * <h1>更新借阅书籍的提醒时间</h1>
     * <p>每天凌晨两点执行处理方法</p>
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void watchBorrowedBook() {
        List<User> users = userRepository.findAll();
        List<BorrowedWatcher> watchers = new ArrayList<>();

        users.forEach(user -> {
            // 如果登录成功，获取数据，设置时间
            if (bookService.login(user.getSnum(), user.getSpwd())) {
                List<BorrowBook> borrowBooks = bookService.borrowedBook();
                borrowBooks.forEach(book -> {
                    Calendar calendar = Calendar.getInstance();
                    try {
                        calendar.setTime(format.parse(book.getReturnDate()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return;
                    }
                    BorrowedWatcher watcher1 = new BorrowedWatcher();
                    BorrowedWatcher watcher2 = new BorrowedWatcher();
                    BorrowedWatcher watcher3 = new BorrowedWatcher();

                    watcher1.setuId(user.getId());
                    watcher2.setuId(user.getId());
                    watcher3.setuId(user.getId());

                    watcher1.setBookName(book.getBookName());
                    watcher2.setBookName(book.getBookName());
                    watcher3.setBookName(book.getBookName());

                    watcher1.setEmail(user.getEmail());
                    watcher2.setEmail(user.getEmail());
                    watcher3.setEmail(user.getEmail());

                    // 一天前
                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                    watcher1.setCallDate(calendar.getTime());

                    // 两天前
                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                    watcher2.setCallDate(calendar.getTime());

                    // 三天前
                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                    watcher3.setCallDate(calendar.getTime());

                    watchers.add(watcher1);
                    watchers.add(watcher2);
                    watchers.add(watcher3);
                });
            }
        }); //end user.forEach()

        // 保存
        borrowedWatcherRepository.save(watchers);
    }

    /**
     * 发送超期提醒邮件
     * 每天早上7点检查是否有要发送的邮件
     */
    @Scheduled(cron = "0 0 7 * * ?")
    public void sendBorrowedEmail() {
        List<BorrowedWatcher> watchers = borrowedWatcherRepository.findAll();
        String date = format.format(new Date());
        List<SimpleMailMessage> mailMessages = new ArrayList<>();
        watchers.forEach(watcher -> {
            String expect = format.format(watcher.getCallDate());
            if (date.equals(expect)) { // 需要今天发送消息
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom("17865923028@163.com");
                message.setTo(watcher.getEmail());
                message.setSubject("乐享其成❤超期提醒");
                message.setText("您的书籍：" + watcher.getBookName() + "将要超期，请注意还书！");

                mailMessages.add(message);
            }
        });
        // 发送邮件
        javaMailSender.send((mailMessages.toArray(new SimpleMailMessage[0])));
    }

    /**
     * 定时查找订阅的书籍是否有更新，如果找到更新，发送邮件提醒
     * <p>
     * 每天5点，10点，下午4点运行定时任务
     */
    @Scheduled(cron = "0 0 5,10,16 * * ?")
    public void searchSubscription() {
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        List<SimpleMailMessage> mailMessages = new ArrayList<>();

        subscriptions.forEach(subscription -> {
            // 书籍名
            String bookName = subscription.getBookName();
            // 书籍所在列表索引
            int index = subscription.getIndes();
            // 搜索的关键字
            String keywords = subscription.getKeywords();

            Book book = bookService.searchBook(keywords).get(index);

            try {
                int size = Integer.parseInt(book.getAvailable().substring(5));
                if (size > 0) { // 有可借的书了,准备发送邮件
                    User user = userRepository.findOne(subscription.getUserId());
                    if (user == null) return;
                    SimpleMailMessage message = new SimpleMailMessage();
                    message.setFrom("17865923028@163.com");
                    message.setTo(user.getEmail());
                    message.setSubject("乐享其成❤抢书啦");
                    message.setText("您订阅的书籍：" + subscription.getBookName() + "有" + size + "本可借，这次可别让别人再抢先啦！");

                    mailMessages.add(message);
                }
            } catch (NumberFormatException e) {
                return;
            }
        });

        // 发送邮件
        javaMailSender.send(mailMessages.toArray(new SimpleMailMessage[0]));
    }

    /**
     * 更新推荐的书籍
     * 每天凌晨3点更新
     */
    @Scheduled(cron = "0 0 3 * * ?")
    public void updateMLRecommendBook() {
        // 检查用户词向量数据是否加载
        if (userVectorList == null) {
            userVectorList = KNN.getAllUserVector();
            // 如果获取不到，终止方法
            if (userVectorList == null) {
                return;
            }
        }

        System.out.println("加载用户词向量完成-------------------------");

        // 获取所有的user
        userRepository.findAll().forEach(user -> {

            System.out.println(user.getId() + "------------------------------");

            // 获取user的搜索关键词
            List<String> words = new ArrayList<>();
            keywordsRepository.findByUserID(user.getId()).forEach(keyWords -> {
                // 汉语转化为拼音，无奈之举啊!!!
                String pinyin = PinyinUtils.getPingYin(keyWords.getKeywords());

                System.out.println("\t" + pinyin + "-----------------------");

                words.add(pinyin);
            });

            if (words.size() == 0) return;

            float[] userVec = MyVector.getUserWordVector(words);

            UserVector userVector = new UserVector();
            userVector.setVector(userVec);

            // 获取用户类别
            int label = KNN.knn(userVectorList, userVector, 6);

            System.out.println("label" + label + "--------------------------------");

            // 获取推荐的书籍
            List<UserBook> userBooks = new ArrayList<>(50);
            List<UserLabel> userLabels = userLabelRepository.findUserLabelLimit(label, 50);
            if (userLabels.size() == 0) {
                userLabels = userLabelRepository.findUserLabelLimit(9, 50);
            }
            userLabels.forEach(userLabel -> {
                if (userLabel == null) return;
                List<UserRating> ratings = userRatingRepository.findByUserid(userLabel.getUserid());

                if (ratings.size() == 0) return;

                String isbn = ratings.get(0).getIsbn();

                System.out.println("isbn:" + isbn + "---------------------");

                List<UserBook> books = userBookRepository.findByIsbn(isbn);
                if (books != null) {
                    userBooks.addAll(books);
                }
            });

            // 删除现有的推荐
            List<MLRecommend> mlRecommends = mlRecommendResository.findByUserid(user.getId());
            mlRecommendResository.delete(mlRecommends);

            List<MLRecommend> newMlRecommend = new ArrayList<>(userBooks.size());
            userBooks.forEach(book -> {
                MLRecommend recommend = new MLRecommend();
                recommend.setBook(book);
                recommend.setUserid(user.getId());
                newMlRecommend.add(recommend);
            });

            // 重新保存新的推荐，前十个
            mlRecommendResository.save(newMlRecommend.size() > 10 ? newMlRecommend.subList(0, 10) : newMlRecommend);
        });
    }
}
