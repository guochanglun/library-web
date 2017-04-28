package com.gcl.library.service;

import com.gcl.library.bean.Book;
import com.gcl.library.bean.BorrowBook;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
@Scope("session")
public class BookService {

    /**
     * 保存sessionID
     */
    private String PHPSESSID = null;

    /**
     * HttpClient客户端
     */
    @Autowired
    private HttpClient client;

    /**
     * 登录并获取PHPSESSID
     */
    public boolean login(String username, String passwd) {
        // 校验数据
        if (username == null || passwd == null) return false;

        PostMethod post = new PostMethod();
        Cookie[] cookies = null;
        post.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        String url = "http://222.206.65.12/reader/redr_verify.php";
        post.addParameter("number", username);
        post.addParameter("passwd", passwd);
        post.addParameter("select", "cert_no");
        try {
            post.setURI(new URI(url));
            int code = client.executeMethod(post);
            String response = new String(post.getResponseBody(), "utf-8");
            post.releaseConnection();
            if (response == null || response.contains("<form action=\"redr_verify.php\" name=\"frm_login\" method=\"POST\">")) {
                return false;
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 搜索书籍
     *
     * @param name 书籍名
     */
    public List<Book> searchBook(String name) {
        List<Book> list = new ArrayList<>();

        // name转码
        try {
            name = URLEncoder.encode(name, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return list;
        }

        // 默认的查询条件
        String param = "historyCount=1"
                + "&strSearchType=title"
                + "&match_flag=forward"
                + "&showmode=list"
                + "&displaypg=100"
                + "&sort=M_PUB_YEAR"
                + "&orderby=desc"
                + "&doctype=ALL"
                + "&strText=" + name;

        try {
            GetMethod get = new GetMethod("http://222.206.65.12/opac/openlink.php?" + param);

            client.executeMethod(get);
            String html = IOUtils.toString(get.getResponseBodyAsStream(), "utf-8");
            //Document doc = Jsoup.connect("http://222.206.65.12/opac/openlink.php?" + param).get();
            Document doc = Jsoup.parse(html);
            Elements eles = doc.select(".list_books");
            Book b = null;

            for (Element ele : eles) {
                b = new Book();
                Element h3 = ele.select("h3").get(0);
                Element p = ele.select("p").get(0);

                //得到书名
                String bookName = h3.select("a").text();
                b.setBookName(bookName);

                //得到编号
                h3.select("a, span").remove();
                String num = h3.text();
                b.setNum(num);

                //得到书本个数
                String[] bb = p.select("span").get(0).text().split(" ");
                b.setCount(bb[0]);
                b.setAvailable(bb[1]);

                //出版信息
                p.select("span").remove();
                String[] span = p.text().split(" ");
                int t = span.length;
                String author = "";
                for (int i = 0; i < t - 1; i++) {
                    author += span[i];
                }
                b.setAuthor(author);
                b.setPublishInfo(span[t - 1]);

                list.add(b);
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 已经借的书
     */
    public List<BorrowBook> borrowedBook() {
        Document doc = null;
        List<BorrowBook> list = new ArrayList<BorrowBook>();
        try {
            GetMethod getMethod = new GetMethod("http://222.206.65.12/reader/book_lst.php");
            int code = client.executeMethod(getMethod);
            // 获取页面
            String html = IOUtils.toString(getMethod.getResponseBodyAsStream(), "utf-8");
            doc = Jsoup.parse(html);
        } catch (IOException e) {
            e.printStackTrace();
            return list;
        }
        Elements eles = doc.select("tr");
        BorrowBook b = null;
        for (int i = 1; i < eles.size(); i++) {
            Element ele = eles.get(i);
            b = new BorrowBook();
            Elements tds = ele.select("td");
            //条码号
            b.setNum(tds.get(0).text());
            //书名
            b.setBookName(tds.get(1).text());
            //借阅日期
            b.setBorrowDate(tds.get(3).text());
            //应还日期
            b.setReturnDate(tds.get(4).text());
            //馆藏地
            b.setPlace(tds.get(5).text());

            list.add(b);
        }
        return list;
    }


    public HttpClient getClient() {
        return client;
    }

    public void setClient(HttpClient client) {
        this.client = client;
    }
}
