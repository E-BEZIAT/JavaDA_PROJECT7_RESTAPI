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
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    /**
     * Permet d'afficher la liste des Users
     *
     * @param model modèle de la vue
     * @return La page contenant la liste des Users
     */
    @RequestMapping("/user/list")
    public String home(Model model) {
        model.addAttribute("users", userRepository.findAll());

        return "user/list";
    }

    /**
     * permet d'afficher la page de création de User
     *
     * @param model modèle de la vue
     * @return retourne la page de création de User
     */
    @GetMapping("/user/add")
    public String addUser(Model model) {
        model.addAttribute("user", new UserParameter());

        return "user/add";
    }

    /**
     * Permet de créer un nouveau User
     *
     * @param user body à remplir lors de la création d'un nouveau User
     * @param result Vérifie si il y a eu des erreurs lors de la création d'un User
     * @param model modèle de la vue
     * @return Si succès, retourne la page contenant la liste des Users, sinon retourne la page de création de User
     */
    @PostMapping("/user/validate")
    public String validate(@Valid @ModelAttribute("user") UserParameter user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "redirect:/user/add";
        }
        userService.createUser(user);
        model.addAttribute("message", "User created successfully");

        return "redirect:/user/list";
    }

    /**
     * Permet d'afficher la page d'update d'un User
     *
     * @param id id du User à update
     * @param model modèle de la vue
     * @return retourne la page de login si l'id est null, sinon retourne la page d'update du User
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        if (id == null) {
            return "redirect:/login";
        }
        UserDTO userDTO = userService.readUser(id);
        model.addAttribute("user", userDTO);

        return "user/update";
    }

    /**
     * Permet d'update un User
     *
     * @param user body à remplir lors de l'update d'un User
     * @param result Vérifie si il y a eu des erreurs lors de l'update d'un User
     * @param model modèle de la vue
     * @return Si succès, retourne la page contenant la liste des users, sinon retourne la page d'update du User.
     * Si id est null, retourne la page de login
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(
            @PathVariable("id") Integer id,
            @Valid @ModelAttribute("user") UserParameter user,
            BindingResult result,
            Model model
    ){
        if (result.hasErrors()) {
            return "user/update";
        }
        userService.updateUser(id, user);
        model.addAttribute("success", true);

        return "redirect:/user/list";
    }

    /**
     * Permet de supprimer un User
     *
     * @param id id du User à supprimer
     * @param model modèle de la vue
     * @return retourne la page contenant la liste des Users
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        userService.deleteUser(id);
        model.addAttribute("users", userRepository.findAll());

        return "redirect:/user/list";
    }
}
