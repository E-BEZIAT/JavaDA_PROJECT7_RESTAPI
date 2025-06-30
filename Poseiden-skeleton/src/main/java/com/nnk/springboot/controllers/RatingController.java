package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.parameters.RatingParameter;
import com.nnk.springboot.domain.response.RatingDTO;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.RatingService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RatingController {
    private final RatingRepository ratingRepository;
    private final RatingService ratingService;

    public RatingController(RatingRepository ratingRepository, RatingService ratingService) {
        this.ratingRepository = ratingRepository;
        this.ratingService = ratingService;
    }
    // TODO: Inject Rating service

    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        // TODO: find all Rating, add to model
        model.addAttribute("ratings", ratingRepository.findAll());
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Model model) {
        model.addAttribute("rating", new RatingParameter());
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid RatingParameter rating, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Rating list
        if (result.hasErrors()) {
            return "rating/list";
        }
        ratingService.createRating(rating);
        model.addAttribute("message", "Rating created successfully");
        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Rating by Id and to model then show to the form
        RatingDTO ratingDTO = ratingService.readRating(id);
        model.addAttribute("rating", ratingDTO);
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid RatingParameter rating,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Rating and return Rating list
        if (result.hasErrors()) {
            return "rating/update";
        }

        ratingService.updateRating(rating, id);
        model.addAttribute("success", true);
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Rating by Id and delete the Rating, return to Rating list
        ratingService.deleteRating(id);
        model.addAttribute("ratings", ratingRepository.findAll());
        return "redirect:/rating/list";
    }
}
