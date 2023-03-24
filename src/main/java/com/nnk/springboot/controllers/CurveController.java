package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.CurveService;
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
public class CurveController {
    @Autowired
    private CurveService curveService;

    /**
     * Method pour l'affichage d'une list
     *
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        return curveService.home(model);
    }

    /**
     * Method pour ajouter une nouvelle entrée
     *
     * @param curve object de type CurvePoint
     * @return des information a la vue pour affichage
     */
    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint curve) {
        return curveService.addBidForm(curve);
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
        return curveService.validate(curvePoint,result,model);
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
        return curveService.showUpdateForm(id,model);
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
        return curveService.updateBid(id,curvePoint,result,model);
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
        return curveService.deleteBid(id,model);
    }
}
