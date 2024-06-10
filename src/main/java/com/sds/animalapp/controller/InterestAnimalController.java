package com.sds.animalapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sds.animalapp.domain.InterestAnimal;
import com.sds.animalapp.domain.Member;
import com.sds.animalapp.model.animal.InterestAnimalService;
import com.sds.animalapp.model.member.MemberDAO;
import com.sds.animalapp.model.member.MemberService;

@Controller
public class InterestAnimalController {

	@Autowired
	private InterestAnimalService interestAnimalService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberDAO memberDAO;

	@PostMapping("/animal/registerInterest")
	@ResponseBody
	public String registerInterest(@RequestParam("animal_idx") int animalIdx, @RequestParam("uid") String uid) {
		// 사용자의 UID를 이용하여 사용자 정보를 가져옴
		Member member = memberDAO.selectByUid(uid);
		if (member == null) {
			return "사용자를 찾을 수 없습니다.";
		}

		// 임시로 관심 동물을 등록하는 코드를 작성하겠습니다.
		// 실제로는 로그인한 사용자의 정보를 가져와야 합니다.
		InterestAnimal interestAnimal = new InterestAnimal();
		interestAnimal.setAnimal_idx(animalIdx); // 동물의 인덱스
		interestAnimal.setMember_idx(member.getMember_idx()); // 사용자의 인덱스

		interestAnimalService.addInterestAnimal(interestAnimal);

		return "관심 등록이 완료되었습니다.";
	}
}
