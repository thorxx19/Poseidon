package com.nnk.springboot.controllers;

import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public ModelAndView login() {
        return loginService.login();
    }

    @GetMapping("/secure/article-details")
    public ModelAndView getAllUserArticles() {
        return loginService.getAllUserArticles();
    }

    @GetMapping("/error")
    public ModelAndView error() {
        return loginService.error();
    }
}
