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

@Controller
public class BidListController {
    private final BidListRepository bidListRepository;
    private final BidListService bidListService;
    private final UserRepository userRepository;

    public BidListController(
            BidListRepository bidListRepository,
            BidListService bidListService,
            UserRepository userRepository
    ){
        this.bidListRepository = bidListRepository;
        this.bidListService = bidListService;
        this.userRepository = userRepository;
    }

    /**
     * permet d'accèder à la page des BidList
     *
     * @param model modèle de la vue
     * @param session récupère et stock l'id de l'utilisateur
     * @return retourne la page de la list des BidList
     */
    @GetMapping("/bidList/list")
    public String home(Model model, HttpSession session) {
        model.addAttribute("bidLists", bidListRepository.findAll());
        Integer userId = (Integer) session.getAttribute("id");

        if (userId != null) {
            User user = userRepository.findById(userId).orElse(null);
            model.addAttribute("loggedUsername", user.getUsername());
        }

        return "bidList/list";
    }

    /**
     * permet d'accèder à la page de création de nouveaux BidList
     *
     * @param model modèle de la vue
     * @return retourne la page de création de BidList
     */
    @GetMapping("/bidList/add")
    public String addBidForm(Model model) {
        model.addAttribute("bidList", new BidListParameter());

        return "bidList/add";
    }

    /**
     * permet de créer de nouveaux BidList
     *
     * @param bid body d'un BidList à remplir lors de l'enregistrement
     * @param result Vérifie si il y a eu des erreurs lors de la création d'un BidList
     * @param model modèle de la vue
     * @return en cas de succès, retourne la page de la liste des BidList, sinon retourne la page de création de BidList
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidListParameter bid, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "bidList/add";
        }

        bidListService.createBidList(bid);
        model.addAttribute("message", "bidList created successfully");

        return "bidList/list";
    }

    /**
     * Affiche la page d'update de BidList
     *
     * @param id id de la BidList à update
     * @param model modèle de la vue
     * @return retourne la page d'update d'une BidList
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        BidListDTO bidListDTO = bidListService.readBidList(id);
        model.addAttribute("bidList", bidListDTO);

        return "bidList/update";
    }

    /**
     *
     * @param id id de la BidList à update
     * @param bidList body d'un BidList à update
     * @param result Vérifie si il y a eu des erreurs lors de l"update d'un BidList
     * @param model modèle de la vue
     * @return En cas de succès, retourne la page de la liste des BidList, sinon retourne la page d'update
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(
            @PathVariable("id") Integer id,
            @Valid BidListParameter bidList,
            BindingResult result,
            Model model
    ){
        if (result.hasErrors()) {
            return "bidList/update";
        }

        bidListService.updateBidList(bidList, id);
        model.addAttribute("success", true);

        return "redirect:/bidList/list";
    }

    /**
     * Permet la suppression d'un BidList
     *
     * @param id Id du BidList à supprimer
     * @param model modèle de la vue
     * @return retourne la page de la liste des BidList
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        bidListService.deleteBidList(id);
        model.addAttribute("bidList", bidListRepository.findAll());

        return "redirect:/bidList/list";
    }
}
