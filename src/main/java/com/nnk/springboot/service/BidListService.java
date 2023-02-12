package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import javax.validation.Valid;

/**
 * @author froidefond
 */
@Service
public class BidListService {

    @Autowired
    BidListRepository bidListRepository;

    /**
     * Method pour l'affichage d'une list
     *
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    public String home(Model model) {
        model.addAttribute("bidList", bidListRepository.findAll());
        return "bidList/list";
    }

    /**
     * Method pour ajouter une nouvelle entrée
     *
     * @param bid object de type BidList
     * @return des information a la vue pour affichage
     */
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    /**
     * Method qui valide les champs rentré pour /add et /update
     *
     * @param bid l'object reçu de la vue
     * @param result l'object qui vérifie les errors
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    public String validate(BidList bid, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            bidListRepository.save(bid);
            model.addAttribute("bidList", bidListRepository.findAll());
            return "redirect:/bidList/list";
        }
        return "bidList/add";
    }

    /**
     * Method pour afficher les information dans les champs pour un update
     *
     * @param id l'id de la BidList
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    public String showUpdateForm(Integer id, Model model) {
        BidList bidList = bidListRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Invalid bidList Id:" + id));
        model.addAttribute("bidList", bidList);
        return "bidList/update";
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
    public String updateBid(Integer id, @Valid BidList bidList, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "bidList/update";
        }
        bidList.setBidListId(id);
        bidListRepository.save(bidList);
        model.addAttribute("bidList", bidListRepository.findAll());
        return "redirect:/bidList/list";
    }

    /**
     * Method pour delete une entité en fonction de sont id
     *
     * @param id de la BidList
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    public String deleteBid(Integer id, Model model) {
        BidList bidList = bidListRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid bidList Id:" + id));
        bidListRepository.delete(bidList);
        model.addAttribute("bidList", bidListRepository.findAll());
        return "redirect:/bidList/list";
    }

}
