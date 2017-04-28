package com.gcl.library.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by gcl on 2016/12/18.
 */
@Entity
public class Advice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long uId;
    private String advice;

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

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }
}
