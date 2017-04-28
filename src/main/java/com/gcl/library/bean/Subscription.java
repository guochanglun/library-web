package com.gcl.library.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by gcl on 2016/12/16.
 */
@Entity
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * 用户id
     */
    private long userId;

    /**
     * 关键字
     */
    private String keywords;

    /**
     * 索引位置
     */
    private int indes;

    /**
     * 是否提醒
     */
    private String isCall;

    private String bookName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public int getIndes() {
        return indes;
    }

    public void setIndes(int indes) {
        this.indes = indes;
    }

    public String getIsCall() {
        return isCall;
    }

    public void setIsCall(String isCall) {
        this.isCall = isCall;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
