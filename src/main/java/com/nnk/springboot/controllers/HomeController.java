package com.nnk.springboot.controllers;

import com.nnk.springboot.service.HomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

/**
 * @author froidefond
 */
@Controller
@Slf4j
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
		log.info("Requête pour /");
		String response = homeService.home(user);
		log.info("Réponse retournée pour / : {}", response);
		return response;
	}

}
