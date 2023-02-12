package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

/**
 * @author froidefond
 */
@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    /**
     * Method pour l'affichage d'une list
     *
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    public String home(Model model) {
        model.addAttribute("rating", ratingRepository.findAll());
        return "rating/list";
    }

    /**
     * Method pour ajouter une nouvelle entrée
     *
     * @param rating object de type Rating
     * @return des information a la vue pour affichage
     */
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    /**
     * Method qui valide les champs rentré pour /add et /update
     *
     * @param rating l'object reçu de la vue
     * @param result l'object qui vérifie les errors
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    public String validate(Rating rating, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            ratingRepository.save(rating);
            model.addAttribute("rating", ratingRepository.findAll());
        }
        return "rating/add";
    }

    /**
     * Method pour afficher les information dans les champs pour un update
     *
     * @param id l'id de la Rating
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    public String showUpdateForm(Integer id, Model model) {
        Rating rating = ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
        model.addAttribute("rating", rating);
        return "rating/update";
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
    public String updateRating(Integer id, Rating rating, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "rating/update";
        }
        rating.setId(id);
        ratingRepository.save(rating);
        model.addAttribute("rating", ratingRepository.findAll());
        return "redirect:/rating/list";
    }

    /**
     * Method pour delete une entité en fonction de sont id
     *
     * @param id da le rating
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    public String deleteRating(Integer id, Model model) {
        Rating rating = ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
        ratingRepository.delete(rating);
        model.addAttribute("rating", ratingRepository.findAll());
        return "redirect:/rating/list";
    }

}
