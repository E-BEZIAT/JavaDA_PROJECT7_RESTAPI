package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController
{
	private final UserRepository userRepository;

	public HomeController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping("/home")
	public String home(Model model, HttpSession session)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		User user = userRepository.findByUsername(username);
		if (user != null) {
			session.setAttribute("id", user.getId());
		}

		return "home";
	}

	@GetMapping("/admin/home")
	public String adminHome(Model model)
	{
		return "redirect:/bidList/list";
	}


}
