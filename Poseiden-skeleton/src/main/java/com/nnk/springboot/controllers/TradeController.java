package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.domain.parameters.TradeParameter;
import com.nnk.springboot.domain.response.TradeDTO;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.TradeService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TradeController {
    private final TradeRepository tradeRepository;
    private final TradeService tradeService;
    private final UserRepository userRepository;

    public TradeController(TradeRepository tradeRepository, TradeService tradeService, UserRepository userRepository) {
        this.tradeRepository = tradeRepository;
        this.tradeService = tradeService;
        this.userRepository = userRepository;
    }
    // TODO: Inject Trade service

    @RequestMapping("/trade/list")
    public String home(Model model, HttpSession session)
    {
        // TODO: find all Trade, add to model
        model.addAttribute("trades", tradeRepository.findAll());
        Integer userId = (Integer) session.getAttribute("id");
        if (userId != null) {
            User user = userRepository.findById(userId).orElse(null);
            model.addAttribute("loggedUsername", user.getUsername());
        }
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Model model) {
        model.addAttribute("trade", new TradeParameter());
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid TradeParameter trade, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Trade list
        if (result.hasErrors()) {
            return "trade/add";
        }

        tradeService.createTrade(trade);
        model.addAttribute("message", "Trade created successfully");
        return "trade/list";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Trade by Id and to model then show to the form
        TradeDTO tradeDTO = tradeService.readTrade(id);
        model.addAttribute("trade", tradeDTO);
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid TradeParameter trade,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Trade and return Trade list
        if (result.hasErrors()) {
            return "trade/update";
        }

        tradeService.updateTrade(trade, id);
        model.addAttribute("success", true);
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Trade by Id and delete the Trade, return to Trade list
        tradeService.deleteTrade(id);
        model.addAttribute("trades", tradeRepository.findAll());
        return "redirect:/trade/list";
    }
}
