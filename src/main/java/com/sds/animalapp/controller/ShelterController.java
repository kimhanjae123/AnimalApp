package com.sds.animalapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sds.animalapp.common.Pager;
import com.sds.animalapp.domain.Shelter;
import com.sds.animalapp.model.shelter.ShelterApiService;
import com.sds.animalapp.model.shelter.ShelterService;

@Controller
public class ShelterController {
	
	@Autowired
	private ShelterApiService shelterApiService;
	
	@Autowired
	private ShelterService shelterService;
	

	@GetMapping("/shelter/list")
	public String getShelter(Shelter shelter,Model model) throws Exception{
		List shelterList = shelterApiService.getShelterList(shelter);
		model.addAttribute("shelterList",shelterList);
		shelterList.forEach(System.out::println);
		shelterService.insert(shelterList);
		return "shelter/list";
	}
	
	@GetMapping("/shelter/detail")
	public String getDetail(){
		return "shelter/detail";
	}
	
	
}