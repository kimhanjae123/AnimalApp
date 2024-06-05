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
import com.sds.animalapp.model.member.MemberService;
import com.sds.animalapp.model.volunteer.VolunteerApplicationService;
import com.sds.animalapp.model.volunteer.VolunteerService;

@Controller
public class VolunteerController {

	@Autowired
	private VolunteerService volunteerService;
	
	@Autowired
    private VolunteerApplicationService volunteerApplicationService;

    @Autowired
    private MemberService memberService;

	@GetMapping("/volunteer/list")
	public String volunteerList(Model model, @RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
			@RequestParam(value = "keyword", defaultValue = "") String keyword) {
		Pager pager = new Pager();
		pager.init(volunteerService.selectCount(keyword), currentPage);

		// 직관적이지 못해서? startIndex, rowCount, keyword를 해시맵으로 넘기지 않고 각 변수 세개로 넘겨도 좋고
		// 파라미터클래스를 만드는 것도 좋다.
		HashMap<String, Object> map = new HashMap();
		map.put("startIndex", pager.getStartIndex());
		map.put("rowCount", pager.getPageSize());
		map.put("keyword", keyword);

		// 봉사 목록 가져오기
		List volunteerList = volunteerService.selectAll(map);

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
        model.addAttribute("detail", volunteerNotice);

        return "volunteer/detail";
    }

	@GetMapping("/volunteer/mypage")
    public String myPageRedirect() {
        // /member/mypage로 리다이렉트
        return "redirect:/member/mypage";
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