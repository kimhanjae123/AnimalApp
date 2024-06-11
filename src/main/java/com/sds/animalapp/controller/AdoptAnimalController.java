package com.sds.animalapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sds.animalapp.domain.AdoptAnimal;
import com.sds.animalapp.model.animal.AdoptAnimalService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class AdoptAnimalController {

	@Autowired
	private AdoptAnimalService adoptAnimalService;

	@PostMapping("/animal/registerAdopt")
	@ResponseBody
	public String registerAdopt(@RequestParam("animal_idx") int animal_idx,
			@RequestParam("member_idx") int member_idx) {
		// 로그 추가
		log.debug("Received animal_idx: " + animal_idx);
		log.debug("Received member_idx: " + member_idx);

		AdoptAnimal adoptAnimal = new AdoptAnimal();
		adoptAnimal.setAnimal_idx(animal_idx);
		adoptAnimal.setMember_idx(member_idx);

		try {
			adoptAnimalService.addAdoptAnimal(adoptAnimal);
			return "입양 신청이 완료되었습니다.";
		} catch (IllegalArgumentException e) {
			return "이미 입양 신청 된 동물입니다.";
		}

	}
}
