package com.gcl.test;

import com.gcl.library.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.mail.Message;

/**
 * Created by gcl on 2016/12/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class TestSendEmail {

    @Autowired
    private JavaMailSender mailSender;

    @Test
    public void sendEmailTest() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("17865923028@163.com");
        message.setTo("1455775688@qq.com");
        message.setSubject("乐享其成❤超期提醒");
        message.setText("您的书籍：将要超期，请注意还书！");

        // 发送邮件
        mailSender.send(message);
    }
}
