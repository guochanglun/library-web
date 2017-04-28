package com.gcl.library.controller;

import com.gcl.library.bean.User;
import com.gcl.library.repository.UserRepository;
import com.gcl.library.service.BookService;
import com.gcl.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created by gcl on 2016/12/16.
 * <p>
 * 用户操作
 */
@RestController
@RequestMapping("/user")
@Scope("session")
public class UserController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    /**
     * 登录系统
     */
    @PostMapping(value = "/login")
    public boolean login(User user, HttpSession session) {
        User u = userService.login(user);
        if (u != null) {
            session.removeAttribute("loginLib");
            session.setAttribute("user", u);
            return true;
        }
        return false;
    }

    /**
     * 登录图书馆
     */
    @PostMapping(value = "/library")
    public boolean loginLibrary(User user, HttpSession session) {
        boolean result = bookService.login(user.getSnum(), user.getSpwd());
        if (result) {  // 如果登录成功，保存学号和密码
            User u = (User) session.getAttribute("user");
            u.setSnum(user.getSnum());
            u.setSpwd(user.getSpwd());
            userRepository.save(u);
            session.setAttribute("user", u);
            session.setAttribute("loginLib", "loginLib");
        }
        return result;
    }

    /**
     * 检测是否登录到图书馆
     */
    @GetMapping(value = "/loginLib")
    public boolean loginLib(HttpSession session) {
        if (session.getAttribute("loginLib") == null) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                return loginLibrary(user, session);
            }
        }
        return true;
    }

    /**
     * 注册
     */
    @PostMapping(value = "/register")
    public boolean register(User user) {
        return userService.register(user);
    }

    /**
     * 退出登录
     */
    @GetMapping(value = "/logout")
    public boolean logout(HttpSession session) {
        session.removeAttribute("user");
        session.removeAttribute("loginLib");
        return true;
    }

    /**
     * 修改信息
     */
    @PostMapping(value = "/change-info")
    public boolean changeInfo(User user, HttpSession session) {
        User u = (User) session.getAttribute("user");
        u.setName(user.getName());
        u.setSign(user.getSign());
        System.out.println(user.getName());
        userRepository.save(u);
        session.setAttribute("user", u);
        return true;
    }

    /**
     * 修改密码
     */
    @PostMapping(value = "/change-pwd/{pwd}/{npwd}")
    public boolean changePwd(@PathVariable("pwd") String pwd, @PathVariable("npwd") String npwd, HttpSession session) {
        User u = (User) session.getAttribute("user");
        if (u.getPassword().equals(pwd)) {
            u.setPassword(npwd);
            userRepository.save(u);
            session.setAttribute("user", u);
            return true;
        }
        return false;
    }
}
