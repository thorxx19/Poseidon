package com.nnk.springboot.controllers;


import com.nnk.springboot.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author froidefond
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * Method pour renvoyer la page de connection
     *
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    @GetMapping("/login")
    public ModelAndView login(Model model) {
        return loginService.login();
    }

    /**
     * Method pour renvoyer la page
     *
     * @return des information a la vue pour affichage
     */
    @GetMapping("/secure/article-details")
    public ModelAndView getAllUserArticles() {
        return loginService.getAllUserArticles();
    }

    /**
     * Method pour renvoyer la page error 403
     *
     * @return des information a la vue pour affichage
     */
    @GetMapping("/403")
    public ModelAndView error() {
        return loginService.error();
    }
}
