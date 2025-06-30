package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.parameters.UserParameter;
import com.nnk.springboot.domain.response.UserDTO;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @RequestMapping("/user/list")
    public String home(Model model)
    {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(Model model) {
        model.addAttribute("user", new UserParameter());
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid UserParameter user, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            return "redirect:/user/list";
        }

        userService.createUser(user);
        model.addAttribute("message", "User created successfully");
        return "user/add";
    }

    @GetMapping("/user/update/")
    public String showUpdateForm(HttpSession httpSession, Model model) {
        Integer id = (Integer) httpSession.getAttribute("id");
        if (id == null) {
            return "redirect:/login";
        }

        UserDTO userDTO = userService.readUser(id);
        model.addAttribute("user", userDTO);
        return "user/update";
    }

    @PostMapping("/user/update/")
    public String updateUser(@Valid UserParameter user, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            return "user/update";
        }

        Integer id = (Integer) session.getAttribute("id");
        if (id == null) {
            return "redirect:/login";
        }

        userService.updateUser(id, user);
        model.addAttribute("success", true);
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        userService.deleteUser(id);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/list";
    }
}
