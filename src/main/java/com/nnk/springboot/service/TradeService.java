package com.nnk.springboot.service;


import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;


@Service
public class TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    public String home(Model model) {
        model.addAttribute("trade", tradeRepository.findAll());
        return "trade/list";
    }

    public String addUser(Trade bid) {
        return "trade/add";
    }

    public String validate(Trade trade, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            tradeRepository.save(trade);
            model.addAttribute("trade", tradeRepository.findAll());
            return "redirect:/trade/list";
        }
        return "trade/add";
    }

    public String showUpdateForm(Integer id, Model model) {
        Trade trade = tradeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
        model.addAttribute("trade", trade);
        return "trade/update";
    }

    public String updateTrade(Integer id, Trade trade, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "trade/update";
        }
        trade.setTradeId(id);
        tradeRepository.save(trade);
        model.addAttribute("trade", tradeRepository.findAll());
        return "redirect:/trade/list";
    }

    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        Trade trade = tradeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
        tradeRepository.delete(trade);
        model.addAttribute("trade", tradeRepository.findAll());
        return "redirect:/trade/list";
    }

}
