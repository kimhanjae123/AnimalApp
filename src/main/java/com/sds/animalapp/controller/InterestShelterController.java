package com.sds.animalapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sds.animalapp.domain.InterestShelter;
import com.sds.animalapp.model.member.MemberDAO;
import com.sds.animalapp.model.shelter.InterestShelterService;

public class InterestShelterController {

	@Autowired
	private InterestShelterService interestShelterService;
	
	@Autowired
	private MemberDAO memberDAO;
	
	@PostMapping("/shelter/interestRegist")
	@ResponseBody
	public String interestRegist(
			@RequestParam("shelter_idx") int shelter_idx, 
			@RequestParam("member_idx") int member_idx) {
		
		InterestShelter interestShelter = new InterestShelter();
		interestShelter.setInterest_shelter_idx(shelter_idx);
		interestShelter.setMember_idx(member_idx);
		
		try {
			interestShelterService.insertInterestShelter(interestShelter);
			return "관심 보호소 등록 완료";
		}catch(IllegalArgumentException e){
			return "등록이 완료된 보호소 입니다";
		}
		
	}
	
}
