package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingService;
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
public class RatingController {
    @Autowired
    private RatingService ratingService;

    /**
     * Method pour l'affichage d'une list
     *
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    @GetMapping("/rating/list")
    public String home(Model model) {
        log.info("Requête GET pour /rating/list");
        String response = ratingService.home(model);
        log.info("Réponse retournée pour /rating/list : {}", response);
        return response;
    }

    /**
     * Method pour ajouter une nouvelle entrée
     *
     * @param rating object de type Rating
     * @return des information a la vue pour affichage
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        log.info("Requête GET pour /rating/add");
        String response = ratingService.addRatingForm(rating);
        log.info("Réponse retournée pour /rating/add : {}", response);
        return response;
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
        log.info("Requête POST pour /rating/validate");
        String response = ratingService.validate(rating,result,model);
        log.info("Réponse retournée pour /rating/validate : {}", response);
        return response;
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
        log.info("Requête GET pour /rating/update/{id}");
        String response = ratingService.showUpdateForm(id,model);
        log.info("Réponse retournée pour /rating/update/{id} : {}", response);
        return response;
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
        log.info("Requête POST pour /rating/update/{id}");
        String response = ratingService.updateRating(id,rating,result,model);
        log.info("Réponse retournée pour /rating/update/{id} : {}", response);
        return response;
    }

    /**
     * Method pour delete une entité en fonction de sont id
     *
     * @param id da le rating
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    @DeleteMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        log.info("Requête DELETE pour /rating/delete/{id}");
        String response = ratingService.deleteRating(id,model);
        log.info("Réponse retournée pour /rating/delete/{id} : {}", response);
        return response;

    }
}
