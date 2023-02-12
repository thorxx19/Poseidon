package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeService;
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
public class TradeController {
    @Autowired
    private TradeService tradeService;

    /**
     * Method pour l'affichage d'une list
     *
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    @RequestMapping("/trade/list")
    public String home(Model model) {
        return tradeService.home(model);
    }

    /**
     * Method pour ajouter une nouvelle entrée
     *
     * @param trade object de type Trade
     * @return des information a la vue pour affichage
     */
    @GetMapping("/trade/add")
    public String addUser(Trade trade) {
        return tradeService.addUser(trade);
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
        return tradeService.validate(trade,result,model);
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
        return tradeService.showUpdateForm(id,model);
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
        return tradeService.updateTrade(id,trade,result,model);
    }

    /**
     * Method pour delete une entité en fonction de sont id
     *
     * @param id de le Trade
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        return tradeService.deleteTrade(id,model);
    }
}
