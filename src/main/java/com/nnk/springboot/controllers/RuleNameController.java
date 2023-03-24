package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.RuleNameService;
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
public class RuleNameController {
    @Autowired
    private RuleNameService ruleNameService;

    /**
     * Method pour l'affichage d'une list
     *
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    @GetMapping("/ruleName/list")
    public String home(Model model) {
        log.info("Requête GET pour /ruleName/list");
        String response = ruleNameService.home(model);
        log.info("Réponse retournée pour /ruleName/list : {}", response);
        return response;
    }

    /**
     * Method pour ajouter une nouvelle entrée
     *
     * @param rule object de type RuleName
     * @return des information a la vue pour affichage
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName rule) {
        log.info("Requête GET pour /ruleName/add");
        String response = ruleNameService.addRuleForm(rule);
        log.info("Réponse retournée pour /ruleName/add : {}", response);
        return response;
    }

    /**
     * Method qui valide les champs rentré pour /add et /update
     *
     * @param ruleName l'object reçu de la vue
     * @param result l'object qui vérifie les errors
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        log.info("Requête POST pour /ruleName/validate");
        String response = ruleNameService.validate(ruleName,result,model);
        log.info("Réponse retournée pour /ruleName/validate : {}", response);
        return response;
    }

    /**
     * Method pour afficher les information dans les champs pour un update
     *
     * @param id l'id de la RuleName
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        log.info("Requête GET pour /ruleName/update/{id}");
        String response = ruleNameService.showUpdateForm(id,model);
        log.info("Réponse retournée pour /ruleName/update/{id} : {}", response);
        return response;
    }

    /**
     * Method pour faire l'update dans la Bdd
     *
     * @param id du RuleName
     * @param ruleName les information récupérer dans la champs dans la vue
     * @param result l'object qui vérifie les errors
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName, BindingResult result, Model model) {
        log.info("Requête POST pour /ruleName/update/{id}");
        String response = ruleNameService.updateRuleName(id,ruleName,result,model);
        log.info("Réponse retournée pour /ruleName/update/{id} : {}", response);
        return response;
    }

    /**
     * Method pour delete une entité en fonction de sont id
     *
     * @param id de la RuleName
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    @DeleteMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        log.info("Requête DELETE pour /ruleName/delete/{id}");
        String response = ruleNameService.deleteRuleName(id,model);
        log.info("Réponse retournée pour /ruleName/delete/{id} : {}", response);
        return response;

    }
}
