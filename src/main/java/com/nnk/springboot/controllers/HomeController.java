package com.nnk.springboot.controllers;

import com.nnk.springboot.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@Autowired
	private HomeService homeService;

	@RequestMapping("/")
	public String home(Model model) {
		return homeService.home(model);
	}

	@RequestMapping("/admin/home")
	public String adminHome(Model model) {
		return homeService.adminHome(model);
	}

}
