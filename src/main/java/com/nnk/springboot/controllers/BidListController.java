package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidListService;
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
public class BidListController {

    @Autowired
    private BidListService bidListService;

    /**
     * Method pour l'affichage d'une list
     *
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    @GetMapping("/bidList/list")
    public String home(Model model) {
        return bidListService.home(model);
    }

    /**
     * Method pour ajouter une nouvelle entrée
     *
     * @param bid object de type BidList
     * @return des information a la vue pour affichage
     */
    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        log.info("Requête GET pour /bidList/list");
        String response = bidListService.addBidForm(bid);
        log.info("Réponse retournée pour /bidList/list : {}", response);
        return response;
    }

    /**
     * Method qui valide les champs rentré pour /add et /update
     *
     * @param bid l'object reçu de la vue
     * @param result l'object qui vérifie les errors
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        log.info("Requête POST pour /bidList/validate");
        String response = bidListService.validate(bid,result,model);
        log.info("Réponse retournée pour /bidList/validate : {}", response);
        return response;
    }

    /**
     * Method pour afficher les information dans les champs pour un update
     *
     * @param id l'id de la BidList
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        log.info("Requête GET pour /bidList/update/{id}");
        String response = bidListService.showUpdateForm(id,model);
        log.info("Réponse retournée pour /bidList/update/{id} : {}", response);
        return response;
    }

    /**
     * Method pour faire l'update dans la Bdd
     *
     * @param id du BidList
     * @param bidList les information récupérer dans la champs dans la vue
     * @param result l'object qui vérifie les errors
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList, BindingResult result, Model model) {
        log.info("Requête POST pour /bidList/update/{id}");
        String response = bidListService.updateBid(id,bidList,result,model);
        log.info("Réponse retournée pour /bidList/update/{id} : {}", response);
        return response;
    }

    /**
     * Method pour delete une entité en fonction de sont id
     *
     * @param id de la BidList
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    @DeleteMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        log.info("Requête DELETE pour /bidList/delete/{id}");
        String response = bidListService.deleteBid(id,model);
        log.info("Réponse retournée pour /bidList/delete/{id} : {}", response);
        return response;
    }
}
