package com.nnk.springboot.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class HomeService {

    public String home(Model model) {
        return "home";
    }

    public String adminHome(Model model) {
        return "redirect:/bidList/list";
    }

}
