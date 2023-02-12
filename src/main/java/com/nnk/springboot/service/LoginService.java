package com.nnk.springboot.service;

import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author froidefond
 */
@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Method pour renvoyer la page de connection
     *
     * @return des information a la vue pour affichage
     */
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

    /**
     * Method pour renvoyer la page
     *
     * @return des information a la vue pour affichage
     */
    public ModelAndView getAllUserArticles() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userRepository.findAll());
        mav.setViewName("user/list");
        return mav;
    }

    /**
     * Method pour renvoyer la page error 403
     *
     * @return des information a la vue pour affichage
     */
    public ModelAndView error() {
        ModelAndView mav = new ModelAndView();
        String errorMessage= "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);
        mav.setViewName("403");
        return mav;
    }

}
