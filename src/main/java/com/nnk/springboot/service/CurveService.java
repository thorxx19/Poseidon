package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;

/**
 * @author froidefond
 */
@Service
public class CurveService {

    @Autowired
    private CurvePointRepository curvePointRepository;

    /**
     * Method pour l'affichage d'une list
     *
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    public String home(Model model) {
        model.addAttribute("curvePoint", curvePointRepository.findAll());
        return "curvePoint/list";
    }

    /**
     * Method pour ajouter une nouvelle entrée
     *
     * @param curve object de type CurvePoint
     * @return des information a la vue pour affichage
     */
    public String addBidForm(CurvePoint curve) {
        return "curvePoint/add";
    }

    /**
     * Method qui valide les champs rentré pour /add et /update
     *
     * @param curvePoint l'object reçu de la vue
     * @param result l'object qui vérifie les error
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    public String validate(CurvePoint curvePoint, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            curvePointRepository.save(curvePoint);
            model.addAttribute("curvePoint", curvePointRepository.findAll());
            return "redirect:/curvePoint/list";
        }
        return "curvePoint/add";
    }
    /**
     * Method pour afficher les information dans les champs pour un update
     *
     * @param id l'id de la CurvePoint
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    public String showUpdateForm(Integer id, Model model) {
        CurvePoint curvePoint = curvePointRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid curvePointId:" + id));
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
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
    public String updateBid(Integer id, @Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "curvePoint/update";
        }
        curvePoint.setCurveId(id);
        curvePointRepository.save(curvePoint);
        model.addAttribute("curvePoint", curvePointRepository.findAll());
        return "redirect:/curvePoint/list";
    }

    /**
     * Method pour delete une entité en fonction de sont id
     *
     * @param id de la CurvePoint
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    public String deleteBid(Integer id, Model model) {
        CurvePoint curvePoint = curvePointRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid curvePoint Id:" + id));
        curvePointRepository.delete(curvePoint);
        model.addAttribute("curvePoint",curvePointRepository.findAll());
        return "redirect:/curvePoint/list";
    }

}
