package com.nnk.springboot.controllers;

import com.nnk.springboot.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

/**
 * @author froidefond
 */
@Controller
public class HomeController {

	@Autowired
	private HomeService homeService;

	/**
	 * Point d'entrée de l'applie
	 *
	 * @param user les information de l'utilisateur
	 * @return des information a la vue pour affichage
	 */
	@RequestMapping("/")
	public String home(Principal user) {
		return homeService.home(user);
	}

	/**
	 * point d'entrée pour les admin
	 *
	 * @param model object de type Model
	 * @return des information a la vue pour affichage
	 */
	@RequestMapping("/admin/home")
	public String adminHome(Model model) {
		return homeService.adminHome(model);
	}

}
