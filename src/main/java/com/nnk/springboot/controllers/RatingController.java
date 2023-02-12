package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingService;
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
public class RatingController {
    @Autowired
    private RatingService ratingService;

    /**
     * Method pour l'affichage d'une list
     *
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    @RequestMapping("/rating/list")
    public String home(Model model) {
        return ratingService.home(model);
    }

    /**
     * Method pour ajouter une nouvelle entrée
     *
     * @param rating object de type Rating
     * @return des information a la vue pour affichage
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return ratingService.addRatingForm(rating);
    }

    /**
     * Method qui valide les champs rentré pour /add et /update
     *
     * @param rating l'object reçu de la vue
     * @param result l'object qui vérifie les errors
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        return ratingService.validate(rating,result,model);
    }

    /**
     * Method pour afficher les information dans les champs pour un update
     *
     * @param id l'id de la Rating
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        return ratingService.showUpdateForm(id,model);
    }

    /**
     * Method pour faire l'update dans la Bdd
     *
     * @param id du Rating
     * @param rating les information récupérer dans la champs dans la vue
     * @param result l'object qui vérifie les errors
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating, BindingResult result, Model model) {
        return ratingService.updateRating(id,rating,result,model);
    }

    /**
     * Method pour delete une entité en fonction de sont id
     *
     * @param id da le rating
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        return ratingService.deleteRating(id,model);
    }
}
