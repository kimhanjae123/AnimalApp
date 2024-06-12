package com.sds.animalapp.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sds.animalapp.common.Pager;
import com.sds.animalapp.domain.VolunteerApplication;
import com.sds.animalapp.domain.VolunteerNotice;
import com.sds.animalapp.domain.VolunteerSelectParam;
import com.sds.animalapp.model.member.MemberService;
import com.sds.animalapp.model.volunteer.VolunteerApplicationService;
import com.sds.animalapp.model.volunteer.VolunteerService;

@Controller
public class VolunteerController {

	@Autowired
	private VolunteerService volunteerService;
	
	@Autowired
    private VolunteerApplicationService volunteerApplicationService;

	@GetMapping("/volunteer/list")
	public String volunteerList(Model model, @RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
			@RequestParam(value = "keyword", defaultValue = "") String keyword) {
		Pager pager = new Pager();
		pager.init(volunteerService.selectCount(keyword), currentPage);
		
		VolunteerSelectParam volunteerSelectParam = new VolunteerSelectParam();
		volunteerSelectParam.setKeyword(keyword);
		volunteerSelectParam.setStartIndex(pager.getStartIndex());
		volunteerSelectParam.setRowCount(pager.getPageSize());

		// 봉사 목록 가져오기
		List<VolunteerNotice> volunteerList = volunteerService.selectAll(volunteerSelectParam);

		// 모델에 데이터 추가
		model.addAttribute("volunteerList", volunteerList);
		model.addAttribute("pager", pager);
		model.addAttribute("keyword", keyword);

		// 뷰 반환
		return "volunteer/list";
	}

	@GetMapping("/volunteer/writeform")
	public String getWriteForm() {
		return "volunteer/regist";
	}

	// 세부창
	@GetMapping("/volunteer/detail")
    public String getDetail(Model model, @RequestParam(value = "id") int id) {
        VolunteerNotice volunteerNotice = volunteerService.select(id);

		//해당 봉사를 신청한 사람 수 불러오기
		int registNum = volunteerService.selectRegistCount(id);
		
        model.addAttribute("detail", volunteerNotice);
        model.addAttribute("registCount", registNum);

        return "volunteer/detail";
    }

	
	@PostMapping("/volunteer/regist")
	public String regist(VolunteerNotice volunteer) {
		volunteerService.insert(volunteer);

		return "redirect:/volunteer/list";
	}
	
	@PostMapping("/volunteer/apply")
	@ResponseBody
	public ResponseEntity<String> apply(@RequestParam("title") String title,
	                                    @RequestParam("volDate") String volDate,
	                                    @RequestParam("noticeId") int noticeId) {
	    VolunteerApplication volunteerApplication = new VolunteerApplication();
	    volunteerApplication.setTitle(title);
	    volunteerApplication.setVolDate(volDate);
	    volunteerApplication.setNoticeId(noticeId);
	    volunteerApplicationService.apply(volunteerApplication);
	    return ResponseEntity.ok("신청 완료");
	}
	
	@PostMapping("/volunteer/cancel")
	@ResponseBody
	public ResponseEntity<String> cancel(@RequestParam("applicationId") int applicationId) {
	    volunteerApplicationService.cancel(applicationId);
	    return ResponseEntity.ok("신청 취소 완료");
	}
}