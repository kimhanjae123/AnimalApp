package com.sds.animalapp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sds.animalapp.common.Pager;
import com.sds.animalapp.domain.Member;
import com.sds.animalapp.domain.VolunteerApplication;
import com.sds.animalapp.domain.VolunteerNotice;
import com.sds.animalapp.domain.VolunteerSelectParam;
import com.sds.animalapp.model.volunteer.VolunteerApplicationService;
import com.sds.animalapp.model.volunteer.VolunteerService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class VolunteerController {

	@Autowired
	private VolunteerService volunteerService;

	@Autowired
	private VolunteerApplicationService volunteerApplicationService;

	@GetMapping("/volunteer/list")
	public String volunteerList(Model model, @RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
			@RequestParam(value = "keyword", defaultValue = "") String keyword) {
		
		System.out.println("이름!! "+keyword);
		
		Pager pager = new Pager();
		pager.init(volunteerService.selectCount(keyword), currentPage);

		VolunteerSelectParam volunteerSelectParam = new VolunteerSelectParam();
		volunteerSelectParam.setKeyword(keyword);
		volunteerSelectParam.setStartIndex(pager.getStartIndex());
		volunteerSelectParam.setRowCount(pager.getPageSize());

		// 봉사 목록 가져오기
		List<VolunteerNotice> volunteerList = volunteerService.selectAll(volunteerSelectParam);
		
		Member member = new Member();

		// 모델에 데이터 추가
		model.addAttribute("volunteerList", volunteerList);
		model.addAttribute("pager", pager);
		model.addAttribute("keyword", keyword);

		// 뷰 반환
		return "volunteer/list";
	}

	@GetMapping("/volunteer/writeform")
	public String getWriteForm(HttpSession session) {
		Member member = (Member) session.getAttribute("member");
        if (member == null) return "redirect:/member/login";
        
		return "volunteer/regist";
	}

	@PostMapping("/volunteer/regist") 
	public String regist(VolunteerNotice volunteer, HttpSession session) { 
		Member member = (Member) session.getAttribute("member");
        if (member == null) return "redirect:/member/login";
        volunteer.setMember_idx(member.getMember_idx());
		volunteerService.insert(volunteer);
		
		return "redirect:/volunteer/list"; 
	}
	
	// 세부창
    @GetMapping("/volunteer/detail")
    public String getDetail(Model model, HttpSession session, @RequestParam(value = "id") int noticeId) {
        Member member = (Member) session.getAttribute("member");
        if (member == null) return "redirect:/member/login";

        VolunteerNotice volunteerNotice = volunteerService.select(noticeId);
        if (volunteerNotice == null) {
            log.error("noticeId 값이 없음 : " + noticeId);
            return "redirect:/error/404";
        }
        
    	// 봉사 게시판의 보호소와 매칭 되는 유기동물보호소 조회
 		Integer shelter_idx = volunteerService.findShelterIdxByCareNm(volunteerNotice.getShelter_name());
 		if (shelter_idx != null) {
 			model.addAttribute("shelter_idx", shelter_idx);
 		}

        // 해당 봉사를 신청한 사람 수 불러오기
        int registNum = volunteerService.selectRegistCount(noticeId);

        int member_idx = member.getMember_idx();
        
        //voluntear_apply 테이블에서 내가 신청한 봉사 기록 확인
        int recordNum = volunteerApplicationService.getRecordNum(noticeId, member_idx);
        
        //해당 게시글의 신청인원 목록 불러오기
        List<Map> applicantNicAndImg = volunteerApplicationService.getAllApplicant(noticeId);

        model.addAttribute("detail", volunteerNotice);
        model.addAttribute("registCount", registNum);
        model.addAttribute("recordNum", recordNum);
        model.addAttribute("applicantNicAndImg", applicantNicAndImg);

        return "volunteer/detail";
    }
    
	  @PostMapping("/volunteer/apply")
	  @ResponseBody public ResponseEntity<String> apply(@RequestParam("id") int id, HttpSession session) { 
		  Member member = (Member)session.getAttribute("member"); 
		  if (member == null) { 
			  return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다."); 
		  }
		  
		  VolunteerNotice volunteerNotice = volunteerService.select(id);
		  
		  if(volunteerNotice == null) { 
			  return ResponseEntity.status(HttpStatus.NOT_FOUND).body("봉사활동을 찾을 수 없습니다."); 
		  }
		  
		  VolunteerApplication volunteerApplication = new VolunteerApplication();
		  volunteerApplication.setTitle(volunteerNotice.getTitle());
		  volunteerApplication.setVol_date(volunteerNotice.getVol_date());
		  volunteerApplication.setNotice_id(volunteerNotice.getVol_event_post_idx());
		  volunteerApplication.setMember_idx(member.getMember_idx());
		  
		  volunteerApplicationService.apply(volunteerApplication); 
		  return ResponseEntity.ok("신청 완료!"); 
	  
	  }
	  
	  @PostMapping("/volunteer/cancel")
	  @ResponseBody
	  public ResponseEntity<String> cancel(@RequestParam("notice_id") int notice_id, HttpSession session) {
		  
	      Member member = (Member) session.getAttribute("member");
	      log.info("notice_id 받은 값 찾기: " + notice_id); // 로그 추가하여 id 값 확인
	      volunteerApplicationService.cancel(notice_id, member.getMember_idx());
	      return ResponseEntity.ok("신청 취소 완료");
	  }

	 

}