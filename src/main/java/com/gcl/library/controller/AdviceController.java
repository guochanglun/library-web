package com.gcl.library.controller;

import com.gcl.library.bean.Advice;
import com.gcl.library.bean.User;
import com.gcl.library.repository.AdviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by gcl on 2016/12/18.
 */
@RestController
@Scope("session")
@RequestMapping("advice")
public class AdviceController {

    @Autowired
    private AdviceRepository adviceRepository;

    /**
     * 添加建议
     */
    @PostMapping
    public boolean advice(@RequestParam("advice") String advice, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Advice ads = new Advice();
        ads.setAdvice(advice);
        ads.setuId(user.getId());
        adviceRepository.save(ads);
        return true;
    }
}
