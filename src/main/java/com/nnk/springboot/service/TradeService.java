package com.nnk.springboot.service;


import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author froidefond
 */
@Service
public class TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    /**
     * Method pour l'affichage d'une list
     *
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    public String home(Model model) {
        model.addAttribute("trade", tradeRepository.findAll());
        return "trade/list";
    }

    /**
     * Method pour ajouter une nouvelle entrée
     *
     * @param trade object de type Trade
     * @return des information a la vue pour affichage
     */
    public String addUser(Trade trade) {
        return "trade/add";
    }

    /**
     * Method qui valide les champs rentré pour /add et /update
     *
     * @param trade l'object reçu de la vue
     * @param result l'object qui vérifie les errors
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    public String validate(Trade trade, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            tradeRepository.save(trade);
            model.addAttribute("trade", tradeRepository.findAll());
            return "redirect:/trade/list";
        }
        return "trade/add";
    }

    /**
     * Method pour afficher les information dans les champs pour un update
     *
     * @param id l'id de la Trade
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    public String showUpdateForm(Integer id, Model model) {
        Trade trade = tradeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
        model.addAttribute("trade", trade);
        return "trade/update";
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
    public String updateTrade(Integer id, Trade trade, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "trade/update";
        }
        trade.setTradeId(id);
        tradeRepository.save(trade);
        model.addAttribute("trade", tradeRepository.findAll());
        return "redirect:/trade/list";
    }

    /**
     * Method pour delete une entité en fonction de sont id
     *
     * @param id de le Trade
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        Trade trade = tradeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
        tradeRepository.delete(trade);
        model.addAttribute("trade", tradeRepository.findAll());
        return "redirect:/trade/list";
    }

}
