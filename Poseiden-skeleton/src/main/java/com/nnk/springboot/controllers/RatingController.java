package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.domain.parameters.RatingParameter;
import com.nnk.springboot.domain.response.RatingDTO;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.RatingService;
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
public class RatingController {
    private final RatingRepository ratingRepository;
    private final RatingService ratingService;
    private final UserRepository userRepository;

    public RatingController(
            RatingRepository ratingRepository,
            RatingService ratingService,
            UserRepository userRepository
    ){
        this.ratingRepository = ratingRepository;
        this.ratingService = ratingService;
        this.userRepository = userRepository;
    }

    /**
     * Permet d'afficher la page de la liste des Rating
     *
     * @param model modèle de la vue
     * @param session récupère et stock l'id de l'utilisateur
     * @return retourne la page de la liste des Rating
     */
    @RequestMapping("/rating/list")
    public String home(Model model, HttpSession session) {
        model.addAttribute("ratings", ratingRepository.findAll());
        Integer userId = (Integer) session.getAttribute("id");

        if (userId != null) {
            User user = userRepository.findById(userId).orElse(null);
            model.addAttribute("loggedUsername", user.getUsername());
        }

        return "rating/list";
    }

    /**
     * Permet d'afficher la page de création de Rating
     *
     * @param model modèle de la vue
     * @return Retourne la page de création des Rating
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Model model) {
        model.addAttribute("rating", new RatingParameter());

        return "rating/add";
    }

    /**
     * Permet de créer un Rating
     *
     * @param rating body du Rating à remplir lors de la création
     * @param result Vérifie si il y a eu des erreurs lors de la création d'un Rating
     * @param model modèle de la vue
     * @return Si succès, retourne la page de la liste des Rating, sinon retourne la page de création du Rating
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid RatingParameter rating, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "rating/add";
        }
        ratingService.createRating(rating);
        model.addAttribute("message", "Rating created successfully");

        return "rating/list";
    }

    /**
     * Permet d'accèder à la page d'update du Rating
     *
     * @param id id du Rating à modifier
     * @param model modèle de la vue
     * @return Retourne la page pour update un Rating
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RatingDTO ratingDTO = ratingService.readRating(id);
        model.addAttribute("rating", ratingDTO);

        return "rating/update";
    }

    /**
     * Permet d'update une Rating
     *
     * @param id id du Rating à update
     * @param rating body du Rating à remplir pour l'update
     * @param result Vérifie si il y a eu des erreurs lors de la l'update d'un Rating
     * @param model modèle de la vue
     * @return Si succès retourne la page de la liste des Rating, sinon retourne la page d'update du Rating
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(
            @PathVariable("id") Integer id,
            @Valid RatingParameter rating,
            BindingResult result,
            Model model
    ){
        if (result.hasErrors()) {
            return "rating/update";
        }

        ratingService.updateRating(rating, id);
        model.addAttribute("success", true);

        return "redirect:/rating/list";
    }

    /**
     * Permet de supprimer un Rating
     *
     * @param id id du rating a update
     * @param model modèle de la vue
     * @return Retourne la page de la liste des Rating
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        ratingService.deleteRating(id);
        model.addAttribute("ratings", ratingRepository.findAll());

        return "redirect:/rating/list";
    }
}
