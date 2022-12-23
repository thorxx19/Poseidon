package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;


@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public String home(Model model) {
        model.addAttribute("rating", ratingRepository.findAll());
        return "rating/list";
    }

    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    public String validate(Rating rating, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            ratingRepository.save(rating);
            model.addAttribute("rating", ratingRepository.findAll());
        }
        return "rating/add";
    }

    public String showUpdateForm(Integer id, Model model) {
        Rating rating = ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
        model.addAttribute("rating", rating);
        return "rating/update";
    }

    public String updateRating(Integer id, Rating rating, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "rating/update";
        }
        rating.setId(id);
        ratingRepository.save(rating);
        model.addAttribute("rating", ratingRepository.findAll());
        return "redirect:/rating/list";
    }

    public String deleteRating(Integer id, Model model) {
        Rating rating = ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
        ratingRepository.delete(rating);
        model.addAttribute("rating", ratingRepository.findAll());
        return "redirect:/rating/list";
    }

}
