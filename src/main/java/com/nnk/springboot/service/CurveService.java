package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;

@Service
public class CurveService {

    @Autowired
    private CurvePointRepository curvePointRepository;

    public String home(Model model) {
        model.addAttribute("curvePoint", curvePointRepository.findAll());
        return "curvePoint/list";
    }

    public String addBidForm(CurvePoint bid) {
        return "curvePoint/add";
    }

    public String validate(CurvePoint curvePoint, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            curvePointRepository.save(curvePoint);
            model.addAttribute("curvePoint", curvePointRepository.findAll());
            return "redirect:/curvePoint/list";
        }
        return "curvePoint/add";
    }

    public String showUpdateForm(Integer id, Model model) {
        CurvePoint curvePoint = curvePointRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid curvePointId:" + id));
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }

    public String updateBid(Integer id, @Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "curvePoint/update";
        }
        curvePoint.setCurveId(id);
        curvePointRepository.save(curvePoint);
        model.addAttribute("curvePoint", curvePointRepository.findAll());
        return "redirect:/curvePoint/list";
    }

    public String deleteBid(Integer id, Model model) {
        CurvePoint curvePoint = curvePointRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid curvePoint Id:" + id));
        curvePointRepository.delete(curvePoint);
        model.addAttribute("curvePoint",curvePointRepository.findAll());
        return "redirect:/curvePoint/list";
    }

}
