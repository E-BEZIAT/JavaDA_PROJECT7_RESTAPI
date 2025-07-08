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

    /**
     * Permet d'accèder à la page de la liste des Trade
     *
     * @param model modèle de la vue
     * @param session récupère et stock l'id de l'utilisateur
     * @return retourne la page de la liste des trade
     */
    @RequestMapping("/trade/list")
    public String home(Model model, HttpSession session) {
        model.addAttribute("trades", tradeRepository.findAll());
        Integer userId = (Integer) session.getAttribute("id");

        if (userId != null) {
            User user = userRepository.findById(userId).orElse(null);
            model.addAttribute("loggedUsername", user.getUsername());
        }

        return "trade/list";
    }

    /**
     * Permet d'afficher la page de création des Trades
     *
     * @param model modèle de la vue
     * @return retourne la page de création du Trade
     */
    @GetMapping("/trade/add")
    public String addUser(Model model) {
        model.addAttribute("trade", new TradeParameter());

        return "trade/add";
    }

    /**
     * Permet de créer un nouveau Trade
     *
     * @param trade body à remplir lors de la création d'un nouveau Trade
     * @param result Vérifie si il y a eu des erreurs lors de la création d'un Trade
     * @param model modèle de la vue
     * @return Si succès, retourne la page contenant la liste des Trades, sinon retourne la page de création du Trade
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid TradeParameter trade, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "trade/add";
        }
        tradeService.createTrade(trade);
        model.addAttribute("message", "Trade created successfully");

        return "trade/list";
    }

    /**
     * Permet d'afficher la page d'update du Trade
     *
     * @param id id du Trade à update
     * @param model modèle de la vue
     * @return Retourne la page d'update du Trade
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        TradeDTO tradeDTO = tradeService.readTrade(id);
        model.addAttribute("trade", tradeDTO);

        return "trade/update";
    }

    /**
     * Permet d'update un Trade
     *
     * @param id id du Trade à update
     * @param trade body à remplir lors de l'update du Trade
     * @param result Vérifie si il y a eu des erreurs lors de l'update d'un Trade
     * @param model modèle de la vue
     * @return Si succès, retourne la page contenant la liste des Trades, sinon retourne la page d'update du Trade
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(
            @PathVariable("id") Integer id,
            @Valid TradeParameter trade,
            BindingResult result,
            Model model
    ){
        if (result.hasErrors()) {
            return "trade/update";
        }
        tradeService.updateTrade(trade, id);
        model.addAttribute("success", true);

        return "redirect:/trade/list";
    }

    /**
     * Permet de supprimer un Trade
     *
     * @param id id du trade à supprimer
     * @param model modèle de la vue
     * @return retourne la page contenant la liste des Trades
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        tradeService.deleteTrade(id);
        model.addAttribute("trades", tradeRepository.findAll());

        return "redirect:/trade/list";
    }
}
