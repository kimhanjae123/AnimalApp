package com.sds.animalapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShelterController {

	@GetMapping("/shelter/list")
	public String getShelter(){
		return "shelter/list";
	}
	
	@GetMapping("/shelter/detail")
	public String getDetail(){
		return "shelter/detail";
	}
}