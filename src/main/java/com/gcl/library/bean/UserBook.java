package com.gcl.library.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by 14557 on 2017/4/28.
 */
@Entity
public class UserBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    // isbn
    private String isbn;
    // 书题目
    private String title;
    // 作者
    private String author;
    // 出版时间
    private String pubtime;
    // 出版社
    private String publisher;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPubtime() {
        return pubtime;
    }

    public void setPubtime(String pubtime) {
        this.pubtime = pubtime;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "UserBook{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", pubtime='" + pubtime + '\'' +
                ", publisher='" + publisher + '\'' +
                '}';
    }
}
