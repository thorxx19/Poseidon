package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;


import javax.validation.Valid;

@Service
public class BidListService {

    @Autowired
    BidListRepository bidListRepository;

    public String home(Model model) {
        model.addAttribute("bidList", bidListRepository.findAll());
        return "bidList/list";
    }

    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    public String validate(BidList bid, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            bidListRepository.save(bid);
            model.addAttribute("bidList", bidListRepository.findAll());
            return "redirect:/bidList/list";
        }
        return "bidList/add";
    }

    public String showUpdateForm(Integer id, Model model) {
        BidList bidList = bidListRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Invalid bidList Id:" + id));
        model.addAttribute("bidList", bidList);
        return "bidList/update";
    }

    public String updateBid(Integer id, @Valid BidList bidList, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "bidList/update";
        }
        bidList.setBidListId(id);
        bidListRepository.save(bidList);
        model.addAttribute("bidList", bidListRepository.findAll());
        return "redirect:/bidList/list";
    }

    public String deleteBid(Integer id, Model model) {
        BidList bidList = bidListRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid bidList Id:" + id));
        bidListRepository.delete(bidList);
        model.addAttribute("bidList", bidListRepository.findAll());
        return "redirect:/bidList/list";
    }

}
