package com.sds.animalapp.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	@GetMapping("/")
    public String getMain(Model model) {
		
		String nickname=SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("nickname", nickname);
		
		
        return "main/index";
    }
}
