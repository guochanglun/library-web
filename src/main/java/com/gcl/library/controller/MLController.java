package com.gcl.library.controller;

import com.gcl.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * Created by 14557 on 2017/4/28.
 */
@Controller
@Scope("session")
public class MLController {

    @Autowired
    private BookService bookService;
}
