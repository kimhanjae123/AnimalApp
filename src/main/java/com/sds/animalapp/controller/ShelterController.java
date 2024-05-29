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
import com.sds.animalapp.domain.VolunteerNotice;
import com.sds.animalapp.model.shelter.ShelterApiService;
import com.sds.animalapp.model.shelter.ShelterService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ShelterController {

	@Autowired
	private ShelterApiService shelterApiService;

	@Autowired
	private ShelterService shelterService;

	@GetMapping("/shelter/list")
	public String getShelter(Shelter shelter, Model model,
			@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
			@RequestParam(value = "keyword", defaultValue = "") String keyword,
			@RequestParam(value = "sidoCode", defaultValue = "00") int sidoCode) throws Exception {
		
		Pager pager = new Pager();
		pager.init(shelterService.selectCount(keyword), currentPage);
		
		log.info(keyword);

		HashMap<String, Object> map = new HashMap();
		map.put("startIndex", pager.getStartIndex());
		map.put("rowCount", pager.getPageSize());
		map.put("keyword", keyword);
		
		List shelterList = shelterService.selectAll(map);
		
		

		model.addAttribute("pager", pager);
		model.addAttribute("shelterList", shelterList);
		model.addAttribute("keyword", keyword);
		model.addAttribute("sidoCode", sidoCode);
		
		log.info(String.valueOf(sidoCode));

		return "shelter/list";
	}

	// 세부창
	@GetMapping("/shelter/detail")
	public String getDetail(Model model, @RequestParam(value = "id") int shelter_idx) {
		Shelter shelter = shelterService.select(shelter_idx);

		model.addAttribute("detail", shelter);

		return "shelter/detail";
	}

}