package com.gcl.library.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by gcl on 2016/12/16.
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * email
     */
    private String email;

    /**
     * 账户密码
     */
    private String password;

    /**
     * 昵称
     */
    private String name;

    /**
     * 个性签名
     */
    private String sign;

    /**
     * 头像
     */
    private String avetar;

    /**
     * 学号
     */
    private String snum;

    /**
     * 图书馆密码
     */
    private String spwd;

    /**
     * 是否订阅消息
     */
    private String isSend;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAvetar() {
        return avetar;
    }

    public void setAvetar(String avetar) {
        this.avetar = avetar;
    }

    public String getSnum() {
        return snum;
    }

    public void setSnum(String snum) {
        this.snum = snum;
    }

    public String getSpwd() {
        return spwd;
    }

    public void setSpwd(String spwd) {
        this.spwd = spwd;
    }

    public String getIsSend() {
        return isSend;
    }

    public void setIsSend(String isSend) {
        this.isSend = isSend;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", sign='" + sign + '\'' +
                ", avetar='" + avetar + '\'' +
                ", snum='" + snum + '\'' +
                ", spwd='" + spwd + '\'' +
                ", isSend='" + isSend + '\'' +
                '}';
    }
}
