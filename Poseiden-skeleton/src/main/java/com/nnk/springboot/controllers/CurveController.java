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

    public CurveController(CurvePointRepository curvePointRepository, CurvePointService curvePointService, UserRepository userRepository) {
        this.curvePointRepository = curvePointRepository;
        this.curvePointService = curvePointService;
        this.userRepository = userRepository;
    }
    // TODO: Inject Curve Point service

    @RequestMapping("/curvePoint/list")
    public String home(Model model, HttpSession session)
    {
        // TODO: find all Curve Point, add to model
        model.addAttribute("curvePoints", curvePointRepository.findAll());
        Integer userId = (Integer) session.getAttribute("id");
        if (userId != null) {
            User user = userRepository.findById(userId).orElse(null);
            model.addAttribute("loggedUsername", user.getUsername());
        }
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(Model model) {
        model.addAttribute("curvePoint", new CurvePointParameter());
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid @ModelAttribute("curvePoint") CurvePointParameter curvePoint, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Curve list
        if (result.hasErrors()) {
            return "curvePoint/add";
        }

        curvePointService.createCurvePoint(curvePoint);
        model.addAttribute("message", "CurvePoint created successfully");
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get CurvePoint by Id and to model then show to the form
        CurvePointDTO curvePointDTO = curvePointService.readCurvePoint(id);
        model.addAttribute("curvePoint", curvePointDTO);
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePointParameter curvePoint,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Curve and return Curve list
        if (result.hasErrors()) {
            return "curvePoint/update";
        }

        curvePointService.updateCurvePoint(curvePoint, id);
        model.addAttribute("success", true);
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Curve by Id and delete the Curve, return to Curve list
        curvePointService.deleteCurvePoint(id);
        model.addAttribute("curvePoint", curvePointRepository.findAll());
        return "redirect:/curvePoint/list";
    }
}
