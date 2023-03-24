package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author froidefond
 */
@Service
public class RuleNameService {

    @Autowired
    private RuleNameRepository ruleNameRepository;

    /**
     * Method pour l'affichage d'une list
     *
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    public String home(Model model) {
        model.addAttribute("ruleName", ruleNameRepository.findAll());
        return "ruleName/list";
    }

    /**
     * Method pour ajouter une nouvelle entrée
     *
     * @param rule object de type RuleName
     * @return des information a la vue pour affichage
     */
    public String addRuleForm(RuleName rule) {
        return "ruleName/add";
    }

    /**
     * Method qui valide les champs rentré pour /add et /update
     *
     * @param ruleName l'object reçu de la vue
     * @param result l'object qui vérifie les errors
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    public String validate(RuleName ruleName, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            ruleNameRepository.save(ruleName);
            model.addAttribute("ruleName", ruleNameRepository.findAll());
            return "redirect:/ruleName/list";
        }
        return "ruleName/add";
    }

    /**
     * Method pour afficher les information dans les champs pour un update
     *
     * @param id l'id de la RuleName
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    public String showUpdateForm(Integer id, Model model) {
        RuleName ruleName = ruleNameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
        model.addAttribute("ruleName", ruleName);
        return "ruleName/update";
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
    public String updateRuleName(Integer id,RuleName ruleName, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "ruleName/update";
        }
        ruleName.setId(id);
        ruleNameRepository.save(ruleName);
        model.addAttribute("ruleName", ruleNameRepository.findAll());
        return "redirect:/ruleName/list";
    }

    /**
     * Method pour delete une entité en fonction de sont id
     *
     * @param id de la RuleName
     * @param model object de type Model
     * @return des information a la vue pour affichage
     */
    public String deleteRuleName(Integer id, Model model) {
        RuleName ruleName = ruleNameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
        ruleNameRepository.delete(ruleName);
        model.addAttribute("ruleName", ruleNameRepository.findAll());
        return "redirect:/ruleName/list";
    }

}
