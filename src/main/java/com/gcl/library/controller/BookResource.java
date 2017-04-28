package com.gcl.library.controller;

import com.gcl.library.bean.*;
import com.gcl.library.repository.KeywordsRepository;
import com.gcl.library.repository.MLRecommendResository;
import com.gcl.library.repository.SubscriptionRepository;
import com.gcl.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@Scope("session")
@RequestMapping("/books")
public class BookResource {

    @Autowired
    private BookService bookSer;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private KeywordsRepository keywordsRepository;

    @Autowired
    private MLRecommendResository mlRecommendResository;

    /**
     * 查询书籍
     */
    @GetMapping
    public List<Book> search(@RequestParam("name") String name, HttpSession session) {

        // 获取当前登录用户
        User user = (User) session.getAttribute("user");

        // 保存搜索的关键字啊
        KeyWords keywords = keywordsRepository.findByUserIDAndKeywords(user.getId(), name);
        if (keywords == null) {
            keywords = new KeyWords();
            keywords.setKeywords(name);
            keywords.setUserID(user.getId());
            keywordsRepository.save(keywords);
        }

        return bookSer.searchBook(name);
    }

    /**
     * 查询借过的书籍
     */
    @GetMapping(value = "/borrowed")
    public List<BorrowBook> borrowed() {
        return bookSer.borrowedBook();
    }

    /**
     * 订阅书籍
     */
    @GetMapping(value = "/subscription/{key}/{index}/{bookName}")
    public boolean subscription(@PathVariable("key") String key, @PathVariable("index") int index,
                                @PathVariable("bookName") String bookName, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if (user != null) {
            if (subscriptionRepository.findByUserIdAndKeywordsAndIndes(user.getId(), key, index) == null) {
                Subscription subscription = new Subscription();
                subscription.setIndes(index);
                subscription.setIsCall("yes");
                subscription.setKeywords(key);
                subscription.setUserId(user.getId());
                subscription.setBookName(bookName);
                subscriptionRepository.save(subscription);
                return true;
            }
        }
        return false;
    }

    /**
     * 删除订阅
     */
    @GetMapping(value = "/subscription/delete/{id}")
    public boolean subscriptioChange(@PathVariable("id") long id) {
        subscriptionRepository.delete(id);
        return true;
    }

    /**
     * 本地推荐：通过查询的记录推荐图书馆的书
     */
    @GetMapping(value = "/recommendLocalBook")
    public List<Book> recommendLocalBook(HttpSession session) {
        List<Book> list = new ArrayList();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return list;
        }

        List<KeyWords> keyWordsList = keywordsRepository.findByUserID(user.getId());
        keyWordsList.forEach(keyWords -> {
            List<Book> books = bookSer.searchBook(keyWords.getKeywords());
            // 取出前5个book
            list.addAll(books.size() > 5 ? books.subList(0, 5) : books);
        });

        list.sort(Comparator.comparingInt(o -> o.getBookName().charAt(0)));

        return list.size() > 10 ? list.subList(0, 10) : list;
    }

    /**
     * 机器推荐：通过机器学习方法做出智能推荐
     */
    @GetMapping(value = "/recommendMachineBook")
    public List<UserBook> recommendMachineBook(HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<UserBook> userBooks = new ArrayList<>();
        mlRecommendResository.findByUserid(user.getId()).forEach(mlRecommend -> {
            userBooks.add(mlRecommend.getBook());
        });
        return userBooks;
    }
}
