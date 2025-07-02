package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.domain.parameters.BidListParameter;
import com.nnk.springboot.domain.response.BidListDTO;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.BidListService;
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
public class BidListController {
    private final BidListRepository bidListRepository;
    private final BidListService bidListService;
    private final UserRepository userRepository;

    public BidListController(BidListRepository bidListRepository, BidListService bidListService, UserRepository userRepository) {
        this.bidListRepository = bidListRepository;
        this.bidListService = bidListService;
        this.userRepository = userRepository;
    }
    // TODO: Inject Bid service

    @RequestMapping("/bidList/list")
    public String home(Model model, HttpSession session)
    {
        // TODO: call service find all bids to show to the view
        model.addAttribute("bidLists", bidListRepository.findAll());
        Integer userId = (Integer) session.getAttribute("id");
        if (userId != null) {
            User user = userRepository.findById(userId).orElse(null);
            model.addAttribute("loggedUsername", user.getUsername());
        }
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(Model model) {
        model.addAttribute("bidList", new BidListParameter());
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidListParameter bid, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return bid list
        if (result.hasErrors()) {
            return "bidList/list";
        }

        bidListService.createBidList(bid);
        model.addAttribute("message", "bidList created successfully");
        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Bid by Id and to model then show to the form
        BidListDTO bidListDTO = bidListService.readBidList(id);
        model.addAttribute("bidList", bidListDTO);
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidListParameter bidList,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Bid and return list Bid
        if (result.hasErrors()) {
            return "bidList/update";
        }

        bidListService.updateBidList(bidList, id);
        model.addAttribute("success", true);
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list
        bidListService.deleteBidList(id);
        model.addAttribute("bidList", bidListRepository.findAll());
        return "redirect:/bidList/list";
    }
}
