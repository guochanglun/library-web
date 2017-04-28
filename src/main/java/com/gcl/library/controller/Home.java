package com.gcl.library.controller;

import com.gcl.library.bean.Subscription;
import com.gcl.library.bean.User;
import com.gcl.library.repository.SubscriptionRepository;
import com.gcl.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by gcl on 2016/12/15.
 */

@Controller
@Scope("session")
public class Home {

    @Autowired
    private BookService bookService;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @RequestMapping("/")
    public String index(HttpSession session) {
        if (session.getAttribute("user") != null) {
            return "redirect:/home";
        }
        return "index";
    }

    @RequestMapping("/home")
    public String home(HttpSession session) {
        return "home";
    }

    @RequestMapping("/borrow")
    public String borrow(Model model, HttpSession session) {
        model.addAttribute("borrowList", bookService.borrowedBook());
        return "borrow";
    }

    @RequestMapping("/subscription")
    public String subscription(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        List<Subscription> subscriptions = subscriptionRepository.findByUserId(user.getId());
        model.addAttribute("subscriptions", subscriptions);
        return "subscription";
    }

    @RequestMapping("/mine")
    public String mine(Model model, HttpSession session) {
        model.addAttribute("user", session.getAttribute("user"));
        return "mine";
    }

    @RequestMapping("/recommend")
    public String recommend(Model model, HttpSession session) {
        model.addAttribute("user", session.getAttribute("user"));
        return "recommend";
    }
}
