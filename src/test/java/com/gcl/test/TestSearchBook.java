package com.gcl.test;

import com.gcl.library.Application;
import com.gcl.library.bean.Book;
import com.gcl.library.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * Created by gcl on 2016/12/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class TestSearchBook {

    @Autowired
    private BookService bookService;

    @Test
    public void searchBookTest() {
        List<Book> books = bookService.searchBook("html5");
        for (int i = 0; i < 10; i++) {
            Book book = books.get(i);
            String ava = book.getAvailable().substring(5);
            System.out.println(ava + "---------");
        }
    }
}
