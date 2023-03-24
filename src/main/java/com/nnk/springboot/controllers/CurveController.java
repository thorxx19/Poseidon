package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.CurveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author froidefond
 */
@Controller
@Slf4j
public class CurveController {
    @Autowired
    private CurveService curveService;

    /**
     * Method pour l'affichage d'une list
     *
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    @GetMapping("/curvePoint/list")
    public String home(Model model) {
        log.info("Requête GET pour /curvePoint/list");
        String response = curveService.home(model);
        log.info("Réponse retournée pour /curvePoint/list : {}", response);
        return response;
    }

    /**
     * Method pour ajouter une nouvelle entrée
     *
     * @param curve object de type CurvePoint
     * @return des information a la vue pour affichage
     */
    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint curve) {
        log.info("Requête GET pour /curvePoint/add");
        String response = curveService.addBidForm(curve);
        log.info("Réponse retournée pour /curvePoint/add : {}", response);
        return response;
    }

    /**
     * Method qui valide les champs rentré pour /add et /update
     *
     * @param curvePoint l'object reçu de la vue
     * @param result l'object qui vérifie les error
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        log.info("Requête POST pour /curvePoint/validate");
        String response = curveService.validate(curvePoint,result,model);
        log.info("Réponse retournée pour /curvePoint/validate : {}", response);
        return response;
    }

    /**
     * Method pour afficher les information dans les champs pour un update
     *
     * @param id l'id de la CurvePoint
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        log.info("Requête GET pour /curvePoint/update/{id}");
        String response = curveService.showUpdateForm(id,model);
        log.info("Réponse retournée pour /curvePoint/update/{id} : {}", response);
        return response;
    }

    /**
     * Method pour faire l'update dans la Bdd
     *
     * @param id du CurvePoint
     * @param curvePoint les information récupérer dans la champs dans la vue
     * @param result l'object qui vérifie les errors
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint, BindingResult result, Model model) {
        log.info("Requête POST pour /curvePoint/update/{id}");
        String response = curveService.updateBid(id,curvePoint,result,model);
        log.info("Réponse retournée pour /curvePoint/update/{id} : {}", response);
        return response;
    }

    /**
     * Method pour delete une entité en fonction de sont id
     *
     * @param id de la CurvePoint
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    @DeleteMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        log.info("Requête DELETE pour /curvePoint/delete/{id}");
        String response = curveService.deleteBid(id,model);
        log.info("Réponse retournée pour /curvePoint/delete/{id} : {}", response);
        return response;
    }
}
