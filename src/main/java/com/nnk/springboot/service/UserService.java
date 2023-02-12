package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

/**
 * @author froidefond
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Method pour l'affichage d'une list
     *
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    public String home(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    /**
     * Method pour ajouter une nouvelle entrée
     *
     * @param user object de type User
     * @return des information a la vue pour affichage
     */
    public String addUser(User user) {
        return "user/add";
    }

    /**
     * Method qui valide les champs rentré pour /add et /update
     *
     * @param user l'object reçu de la vue
     * @param result l'object qui vérifie les errors
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    public String validate(User user, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);
            model.addAttribute("users", userRepository.findAll());
            return "redirect:/user/list";
        }
        return "user/add";
    }

    /**
     * Method pour afficher les information dans les champs pour un update
     *
     * @param id l'id User
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    public String showUpdateForm(Integer id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        user.setPassword("");
        model.addAttribute("user", user);
        return "user/update";
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
    public String updateUser(Integer id, User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);
        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/list";
    }

    /**
     * Method pour delete une entité en fonction de sont id
     *
     * @param id User
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    public String deleteUser(Integer id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/list";
    }

}
