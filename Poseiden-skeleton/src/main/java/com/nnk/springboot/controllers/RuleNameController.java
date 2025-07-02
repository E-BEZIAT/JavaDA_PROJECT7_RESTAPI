package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.domain.parameters.RuleNameParamater;
import com.nnk.springboot.domain.response.RuleNameDTO;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.RuleNameService;
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
public class RuleNameController {
    private final RuleNameRepository ruleNameRepository;
    private final RuleNameService ruleNameService;
    private final UserRepository userRepository;

    public RuleNameController(RuleNameRepository ruleNameRepository, RuleNameService ruleNameService, UserRepository userRepository) {
        this.ruleNameRepository = ruleNameRepository;
        this.ruleNameService = ruleNameService;
        this.userRepository = userRepository;
    }
    // TODO: Inject RuleName service

    @RequestMapping("/ruleName/list")
    public String home(Model model, HttpSession session)
    {
        // TODO: find all RuleName, add to model
        model.addAttribute("ruleNames", ruleNameRepository.findAll());
        Integer userId = (Integer) session.getAttribute("id");
        if (userId != null) {
            User user = userRepository.findById(userId).orElse(null);
            model.addAttribute("loggedUsername", user.getUsername());
        }
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(Model model) {
        model.addAttribute("ruleName", new RuleNameParamater());
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleNameParamater ruleName, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return RuleName list
        if (result.hasErrors()) {
            return "ruleName/list";
        }

        ruleNameService.createRuleName(ruleName);
        model.addAttribute("message", "RuleName added successfully");
        return "ruleName/add";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get RuleName by Id and to model then show to the form
        RuleNameDTO ruleNameDTO = ruleNameService.readRuleName(id);
        model.addAttribute("ruleName", ruleNameDTO);
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleNameParamater ruleName,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update RuleName and return RuleName list
        if (result.hasErrors()) {
            return "ruleName/update";
        }

        ruleNameService.updateRuleName(ruleName, id);
        model.addAttribute("success", true);
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        // TODO: Find RuleName by Id and delete the RuleName, return to Rule list
        ruleNameService.deleteRuleName(id);
        model.addAttribute("ruleNames", ruleNameRepository.findAll());
        return "redirect:/ruleName/list";
    }
}
