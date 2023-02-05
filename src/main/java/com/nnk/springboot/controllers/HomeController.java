package com.nnk.springboot.controllers;

import com.nnk.springboot.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class HomeController {

	@Autowired
	private HomeService homeService;

	@RequestMapping("/")
	public String home(Principal user) {
		return homeService.home(user);
	}

	@RequestMapping("/admin/home")
	public String adminHome(Model model) {
		return homeService.adminHome(model);
	}

}
