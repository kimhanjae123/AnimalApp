package com.sds.animalapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sds.animalapp.domain.InterestShelter;
import com.sds.animalapp.domain.Member;
import com.sds.animalapp.domain.Shelter;
import com.sds.animalapp.model.shelter.InterestShelterService;
import com.sds.animalapp.model.shelter.ShelterService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class InterestShelterController {

    @Autowired
    private InterestShelterService interestShelterService;

    @Autowired
    private ShelterService shelterService;

    // 보호소 관심 등록 처리
    @PostMapping("/shelter/registerInterestShelter")
    @ResponseBody
    public ResponseEntity<String> registerInterestShelter(
            @RequestParam("shelter_idx") int shelter_idx,
            @RequestParam("member_idx") int member_idx,
            @RequestParam("orgNm") String orgNm,
            @RequestParam("careNm") String careNm, HttpSession session) { 

        log.debug("받은 shelter_idx 값: " + shelter_idx);
        log.debug("받은 member_idx 값: " + member_idx);

        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        Shelter shelter = shelterService.select(shelter_idx);
        if (shelter == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("보호소를 찾을 수 없습니다.");
        }

        InterestShelter interestShelter = new InterestShelter();
        interestShelter.setShelter_idx(shelter_idx);
        interestShelter.setMember_idx(member_idx);
        interestShelter.setCareNm(careNm);
        interestShelter.setOrgNm(orgNm);

        try {
            interestShelterService.insertInterestShelter(interestShelter);
        } catch (Exception e) {
            log.error("관심 보호소 등록 실패: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return ResponseEntity.ok("신청완료");
    }

    // 보호소 관심 등록 취소를 처리
    @PostMapping("/shelter/cancelInterestShelter")
    @ResponseBody
    public ResponseEntity<String> cancelInterestShelter(@RequestParam("shelter_idx") int shelter_idx,
            @RequestParam("member_idx") int member_idx, HttpSession session) {

        log.debug("받은 shelter_idx 값: " + shelter_idx);
        log.debug("받은 member_idx 값: " + member_idx);

        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        try {
            InterestShelter interestShelter = interestShelterService.findInterestByShelterIdxAndMemberIdx(shelter_idx, member_idx);
            if (interestShelter == null) {
                log.debug("관심 보호소 신청 내역을 찾을 수 없습니다. shelter_idx: {}, member_idx: {}", shelter_idx, member_idx);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("관심 보호소 신청 내역을 찾을 수 없습니다.");
            }
            log.debug("관심 보호소 찾기 interest shelter: {}", interestShelter);
            interestShelterService.deleteInterestShelter(interestShelter.getInterest_shelter_idx());
        } catch (Exception e) {
            log.error("관심 보호소 신청 취소 실패: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("관심 보호소 신청 취소 실패: " + e.getMessage());
        }

        return ResponseEntity.ok("신청 취소 완료");
    }

    // 보호소 관심 상태 확인
    @PostMapping("/shelter/checkInterestShelter")
    @ResponseBody
    public ResponseEntity<Boolean> checkInterestShelter(@RequestParam("shelter_idx") int shelter_idx,
            @RequestParam("member_idx") int member_idx, HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }

        InterestShelter interestShelter = interestShelterService.findInterestByShelterIdxAndMemberIdx(shelter_idx, member_idx);
        return ResponseEntity.ok(interestShelter != null);
    }
}
