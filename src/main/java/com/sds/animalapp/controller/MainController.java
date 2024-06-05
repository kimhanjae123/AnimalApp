package com.sds.animalapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sds.animalapp.model.animal.AnimalService;

@Controller
public class MainController {
	
	@Autowired
	private AnimalService animalService;
	
	@GetMapping("/")
    public String getMain(Model model) {
		
		String nickname=SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("nickname", nickname);
		
		List animalList = animalService.selectPreview();
		
		model.addAttribute("animalList", animalList);
		
        return "main/index";
    }
}
