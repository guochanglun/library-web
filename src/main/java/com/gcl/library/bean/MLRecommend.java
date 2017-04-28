package com.gcl.library.bean;

import javax.persistence.*;

/**
 * Created by 14557 on 2017/4/28.
 */
@Entity
public class MLRecommend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long userid;

    @OneToOne(targetEntity = UserBook.class)
    private UserBook book;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public UserBook getBook() {
        return book;
    }

    public void setBook(UserBook book) {
        this.book = book;
    }
}
