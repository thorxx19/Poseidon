package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author froidefond
 */
@Controller
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Method pour l'affichage d'une list
     *
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    @GetMapping("/user/list")
    public String home(Model model) {
        log.info("Requête GET pour /user/list");
        String response = userService.home(model);
        log.info("Réponse retournée pour /user/list : {}", response);
        return response;
    }

    /**
     * Method pour ajouter une nouvelle entrée
     *
     * @param user object de type User
     * @return des information a la vue pour affichage
     */
    @GetMapping("/user/add")
    public String addUser(User user) {
        log.info("Requête GET pour /user/add");
        String response = userService.addUser(user);
        log.info("Réponse retournée pour /user/add : {}", response);
        return response;
    }

    /**
     * Method qui valide les champs rentré pour /add et /update
     *
     * @param user l'object reçu de la vue
     * @param result l'object qui vérifie les errors
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        log.info("Requête POST pour /user/validate");
        String response = userService.validate(user,result,model);
        log.info("Réponse retournée pour /user/validate : {}", response);
        return response;
    }

    /**
     * Method pour afficher les information dans les champs pour un update
     *
     * @param id l'id User
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        log.info("Requête GET pour /user/update/{id}");
        String response = userService.showUpdateForm(id,model);
        log.info("Réponse retournée pour /user/update/{id} : {}", response);
        return response;
    }

    /**
     * Method pour faire l'update dans la Bdd
     *
     * @param id User
     * @param user les information récupérer dans la champs dans la vue
     * @param result l'object qui vérifie les errors
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user, BindingResult result, Model model) {
        log.info("Requête POST pour /user/update/{id}");
        String response = userService.updateUser(id,user,result,model);
        log.info("Réponse retournée pour /user/update/{id} : {}", response);
        return response;
    }

    /**
     * Method pour delete une entité en fonction de sont id
     *
     * @param id User
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    @DeleteMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        log.info("Requête DELETE pour /user/delete/{id}");
        String response = userService.deleteUser(id, model);
        log.info("Réponse retournée pour /user/delete/{id} : {}", response);
        return response;
    }
}
