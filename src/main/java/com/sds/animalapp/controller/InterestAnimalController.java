package com.sds.animalapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sds.animalapp.domain.InterestAnimal;
import com.sds.animalapp.model.animal.InterestAnimalService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class InterestAnimalController {

	@Autowired
	private InterestAnimalService interestAnimalService;

	@PostMapping("/animal/registerInterest")
	@ResponseBody
	public String registerInterest(@RequestParam("animal_idx") int animal_idx,
			@RequestParam("member_idx") int member_idx) {
		// 로그 추가
		log.debug("Received animal_idx: " + animal_idx);
		log.debug("Received member_idx: " + member_idx);

		InterestAnimal interestAnimal = new InterestAnimal();
		interestAnimal.setAnimal_idx(animal_idx); // 동물의 인덱스 설정
		interestAnimal.setMember_idx(member_idx); // 사용자의 인덱스 설정

		try {
			interestAnimalService.addInterestAnimal(interestAnimal);
			return "관심 등록이 완료되었습니다.";
		} catch (IllegalArgumentException e) {
			return "이미 등록된 관심 동물입니다.";
		}
	}
}
