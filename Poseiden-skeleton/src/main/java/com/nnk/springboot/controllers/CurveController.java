package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.domain.parameters.CurvePointParameter;
import com.nnk.springboot.domain.response.CurvePointDTO;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.CurvePointService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class CurveController {
    private final CurvePointRepository curvePointRepository;
    private final CurvePointService curvePointService;
    private final UserRepository userRepository;

    public CurveController(
            CurvePointRepository curvePointRepository,
            CurvePointService curvePointService,
            UserRepository userRepository
    ){
        this.curvePointRepository = curvePointRepository;
        this.curvePointService = curvePointService;
        this.userRepository = userRepository;
    }

    /**
     * Permet d'accèder à la page de la liste des CurvePoint
     *
     * @param model modèle de la vue
     * @param session récupère l'id de l'utilisateur et le stock
     * @return retourne la page de la liste des CurvePoint
     */
    @RequestMapping("/curvePoint/list")
    public String home(Model model, HttpSession session) {
        model.addAttribute("curvePoints", curvePointRepository.findAll());
        Integer userId = (Integer) session.getAttribute("id");

        if (userId != null) {
            User user = userRepository.findById(userId).orElse(null);
            model.addAttribute("loggedUsername", user.getUsername());
        }

        return "curvePoint/list";
    }

    /**
     * Permet d'accèder à la page de création de CurvePoint
     *
     * @param model modèle de la vue
     * @return retourne la page de création de CurvePoint
     */
    @GetMapping("/curvePoint/add")
    public String addBidForm(Model model) {
        model.addAttribute("curvePoint", new CurvePointParameter());

        return "curvePoint/add";
    }

    /**
     * Permet de créer un CurvePoint
     *
     * @param curvePoint body d'un CurvePoint à remplir lors de la création
     * @param result Vérifie si il y a eu des erreurs lors de la création d'un CurvePoint
     * @param model modèle de la vue
     * @return Si succès, retourne la page de la liste des CurvePoint, sinon retourne la page de création de CurvePoint
     */
    @PostMapping("/curvePoint/validate")
    public String validate(
            @Valid @ModelAttribute("curvePoint") CurvePointParameter curvePoint,
            BindingResult result,
            Model model
    ){
        if (result.hasErrors()) {
            return "curvePoint/add";
        }

        curvePointService.createCurvePoint(curvePoint);
        model.addAttribute("message", "CurvePoint created successfully");

        return "redirect:/curvePoint/list";
    }

    /**
     * Permet d'accèder à la page d'update de CurvePoint
     *
     * @param id id du CurvePoint à modifier
     * @param model modèle de la vue
     * @return retourne la page d'update d'un CurvePoint
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        CurvePointDTO curvePointDTO = curvePointService.readCurvePoint(id);
        model.addAttribute("curvePoint", curvePointDTO);

        return "curvePoint/update";
    }

    /**
     * Permet d'update un CurvePoint
     *
     * @param id id du CurvePoint à modifier
     * @param curvePoint body à remplir du CurvePoint à modifier
     * @param result Vérifie si il y a eu des erreurs lors de la création d'un BidList
     * @param model modèle de la vue
     * @return Si succès, retourne la page de la liste des CurvePoint, sinon retourne la page d'update du CurvePoint
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(
            @PathVariable("id") Integer id,
            @Valid @ModelAttribute("curvePoint") CurvePointParameter curvePoint,
            BindingResult result,
            Model model
    ){
        if (result.hasErrors()) {
            return "curvePoint/update";
        }

        curvePointService.updateCurvePoint(curvePoint, id);
        model.addAttribute("success", true);

        return "redirect:/curvePoint/list";
    }

    /**
     * Permet de supprimer un CurvePoint
     *
     * @param id id du CurvePoint à supprimer
     * @param model modèle de la vue
     * @return retourne la page de la liste des CurvePoint
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        curvePointService.deleteCurvePoint(id);
        model.addAttribute("curvePoint", curvePointRepository.findAll());

        return "redirect:/curvePoint/list";
    }
}
