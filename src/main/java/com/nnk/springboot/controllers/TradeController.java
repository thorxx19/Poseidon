package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeService;
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
public class TradeController {
    @Autowired
    private TradeService tradeService;

    /**
     * Method pour l'affichage d'une list
     *
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    @GetMapping("/trade/list")
    public String home(Model model) {
        log.info("Requête GET pour /trade/list");
        String response = tradeService.home(model);
        log.info("Réponse retournée pour /trade/list : {}", response);
        return response;
    }

    /**
     * Method pour ajouter une nouvelle entrée
     *
     * @param trade object de type Trade
     * @return des information a la vue pour affichage
     */
    @GetMapping("/trade/add")
    public String addUser(Trade trade) {
        log.info("Requête GET pour /trade/add");
        String response = tradeService.addUser(trade);
        log.info("Réponse retournée pour /trade/add : {}", response);
        return response;
    }

    /**
     * Method qui valide les champs rentré pour /add et /update
     *
     * @param trade l'object reçu de la vue
     * @param result l'object qui vérifie les errors
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        log.info("Requête POST pour /trade/validate");
        String response = tradeService.validate(trade,result,model);
        log.info("Réponse retournée pour /trade/validate : {}", response);
        return response;
    }

    /**
     * Method pour afficher les information dans les champs pour un update
     *
     * @param id l'id de la Trade
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        log.info("Requête GET pour /trade/update/{id}");
        String response = tradeService.showUpdateForm(id,model);
        log.info("Réponse retournée pour /trade/update/{id} : {}", response);
        return response;
    }

    /**
     * Method pour faire l'update dans la Bdd
     *
     * @param id de Trade
     * @param trade les information récupérer dans la champs dans la vue
     * @param result l'object qui vérifie les errors
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade, BindingResult result, Model model) {
        log.info("Requête POST pour /trade/update/{id}");
        String response = tradeService.updateTrade(id,trade,result,model);
        log.info("Réponse retournée pour /trade/update/{id} : {}", response);
        return response;
    }

    /**
     * Method pour delete une entité en fonction de sont id
     *
     * @param id de le Trade
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    @DeleteMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        log.info("Requête DELETE pour /trade/delete/{id}");
        String response = tradeService.deleteTrade(id,model);
        log.info("Réponse retournée pour /trade/delete/{id} : {}", response);
        return response;
    }
}
