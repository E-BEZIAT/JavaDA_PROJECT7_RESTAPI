package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.domain.parameters.RuleNameParameter;
import com.nnk.springboot.domain.response.RuleNameDTO;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.RuleNameService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class RuleNameController {
    private final RuleNameRepository ruleNameRepository;
    private final RuleNameService ruleNameService;
    private final UserRepository userRepository;

    public RuleNameController(
            RuleNameRepository ruleNameRepository,
            RuleNameService ruleNameService,
            UserRepository userRepository
    ){
        this.ruleNameRepository = ruleNameRepository;
        this.ruleNameService = ruleNameService;
        this.userRepository = userRepository;
    }

    /**
     * Permet d'afficher la page de la liste des RuleNames
     *
     * @param model modèle de la vue
     * @param session récupère et stock l'id de l'utilisateur
     * @return retourne la page de la liste des RuleNames
     */
    @RequestMapping("/ruleName/list")
    public String home(Model model, HttpSession session) {
        model.addAttribute("ruleNames", ruleNameRepository.findAll());
        Integer userId = (Integer) session.getAttribute("id");

        if (userId != null) {
            User user = userRepository.findById(userId).orElse(null);
            model.addAttribute("loggedUsername", user.getUsername());
        }

        return "ruleName/list";
    }

    /**
     * Permet d'accèder à la page de création de RuleNames
     *
     * @param model modèle de la vue
     * @return retourne la page de création de RuleNames
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(Model model) {
        model.addAttribute("ruleName", new RuleNameParameter());

        return "ruleName/add";
    }

    /**
     * Permet de créer un nouveau RuleName
     *
     * @param ruleName body à remplir lors de la création de RuleName
     * @param result Vérifie si il y a eu des erreurs lors de la création d'un RuleName
     * @param model modèle de la vue
     * @return Si succès, retourne la page contenant la liste de RuleNames, sinon retourne la page de création de RuleName
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid @ModelAttribute("ruleName") RuleNameParameter ruleName, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "ruleName/add";
        }

        ruleNameService.createRuleName(ruleName);
        model.addAttribute("message", "RuleName added successfully");

        return "redirect:/ruleName/list";
    }

    /**
     * Permet d'accèder à la page d'update d'un RuleName
     *
     * @param id id du RuleName à update
     * @param model modèle de la vue
     * @return retourne la page d'update d'un RuleName
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RuleNameDTO ruleNameDTO = ruleNameService.readRuleName(id);
        model.addAttribute("ruleName", ruleNameDTO);

        return "ruleName/update";
    }

    /**
     * Permet d'update un RuleName
     *
     * @param id id du RuleName à update
     * @param ruleName body à remplir lors de l'update d'un RuleName
     * @param result Vérifie si il y a eu des erreurs lors de l'update d'un RuleName
     * @param model modèle de la vue
     * @return Si succès, retourne la page de la liste des RuleName, sinon retourne la page d'update d'un RuleName
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(
            @PathVariable("id") Integer id,
            @Valid @ModelAttribute("ruleName") RuleNameParameter ruleName,
            BindingResult result,
            Model model
    ){
        if (result.hasErrors()) {
            return "ruleName/update";
        }
        ruleNameService.updateRuleName(ruleName, id);
        model.addAttribute("success", true);

        return "redirect:/ruleName/list";
    }

    /**
     * Permet de supprimer un RuleName
     *
     * @param id id du RuleName à supprimer
     * @param model modèle de la vue
     * @return retourne la page de la liste des RuleName
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        ruleNameService.deleteRuleName(id);
        model.addAttribute("ruleNames", ruleNameRepository.findAll());

        return "redirect:/ruleName/list";
    }
}
