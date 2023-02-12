package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * @author froidefond
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Method pour l'affichage d'une list
     *
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    @RequestMapping("/user/list")
    public String home(Model model) {
        return userService.home(model);
    }

    /**
     * Method pour ajouter une nouvelle entrée
     *
     * @param user object de type User
     * @return des information a la vue pour affichage
     */
    @GetMapping("/user/add")
    public String addUser(User user) {
        return userService.addUser(user);
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
        return userService.validate(user,result,model);
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
        return userService.showUpdateForm(id,model);
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
        return userService.updateUser(id,user,result,model);
    }

    /**
     * Method pour delete une entité en fonction de sont id
     *
     * @param id User
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        return userService.deleteUser(id, model);
    }
}
