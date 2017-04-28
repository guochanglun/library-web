package com.gcl.library.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by 14557 on 2017/4/28.
 * <p>
 * 监听程序启动，做一些初始化操作
 */
@WebListener
public class AppStartListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("APP start--------------------------------------");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
