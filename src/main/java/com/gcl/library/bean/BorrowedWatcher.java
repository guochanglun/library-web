package com.gcl.library.bean;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by gcl on 2016/12/18.
 * <p>
 * 借阅的书
 */
@Entity
public class BorrowedWatcher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * user ID
     */
    private long uId;

    /**
     * 书名
     */
    private String bookName;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 提醒的时间
     */
    @Temporal(TemporalType.DATE)
    private Date callDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getuId() {
        return uId;
    }

    public void setuId(long uId) {
        this.uId = uId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Date getCallDate() {
        return callDate;
    }

    public void setCallDate(Date callDate) {
        this.callDate = callDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
