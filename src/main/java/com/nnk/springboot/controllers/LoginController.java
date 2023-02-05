package com.nnk.springboot.controllers;


import com.nnk.springboot.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public ModelAndView login(Model model) {
        return loginService.login();
    }

    @GetMapping("/secure/article-details")
    public ModelAndView getAllUserArticles() {
        return loginService.getAllUserArticles();
    }

    @GetMapping("/403")
    public ModelAndView error() {
        return loginService.error();
    }
}
