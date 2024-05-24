package com.sds.animalapp.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sds.animalapp.domain.Member;
import com.sds.animalapp.model.member.MemberService;
import com.sds.animalapp.model.member.RoleService;
import com.sds.animalapp.model.member.SnsService;
import com.sds.animalapp.sns.KaKaoLogin;
import com.sds.animalapp.sns.KaKaoOAuthToken;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberController {
	
	@Autowired
	private KaKaoLogin kakaoLogin;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private SnsService snsService;

    @Value("${upload.directory}")
    private String uploadDirectory;

    @GetMapping("/member/login")
    public String getLoginForm() {
        return "member/login";
    }

    @GetMapping("/member/mypage")
    public String getMyPage() {
        return "member/mypage";
    }

    @PostMapping("/upload")
    @ResponseBody
    public Map<String, Object> pictureFileInput(@RequestParam("file") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(uploadDirectory, file.getOriginalFilename());

                // Ensure the directories exist
                if (Files.notExists(path.getParent())) {
                    Files.createDirectories(path.getParent());
                }

                Files.write(path, bytes);
                String imageUrl = "/mypage/" + file.getOriginalFilename();
                response.put("success", true);
                response.put("imageUrl", imageUrl);

                // Log 추가
                System.out.println("Image uploaded to: " + path.toString());
                System.out.println("Image URL: " + imageUrl);
            } catch (IOException e) {
                e.printStackTrace();
                response.put("success", false);
                response.put("message", "파일 업로드에 실패했습니다.");
            }
        } else {
            response.put("success", false);
            response.put("message", "업로드할 파일을 선택해주세요.");
        }
        return response;
    }
    
    /*----------------------------------------------------------------
	 카카오 콜백 요청 처리 
	 *---------------------------------------------------------------- */
	@GetMapping("/member/sns/kakao/callback")
	public ModelAndView kakaoCallback(HttpServletRequest request) {
		
		String code  = request.getParameter("code");
		
		log.info("카카오가 보내 임시 코드는 "+code);
		
		MultiValueMap<String, String> params  = new LinkedMultiValueMap<String, String>();
		params.add("code", code);
		params.add("client_id", kakaoLogin.getClient_id());
		params.add("redirect_uri", kakaoLogin.getRedirect_uri());
		params.add("grant_type", kakaoLogin.getGrant_type());
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/x-www-form-urlencoded");
		
		HttpEntity entity = new HttpEntity(params, headers);
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity=restTemplate.exchange(kakaoLogin.getToken_request_url(), HttpMethod.POST, entity, String.class);
		
		String body = responseEntity.getBody();
		log.info("카카오가 보낸 토큰을 포함한 응답정보는 "+body);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		KaKaoOAuthToken oAuthToken=null;
		try {
			oAuthToken = objectMapper.readValue(body, KaKaoOAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer "+oAuthToken.getAccess_token());
		
		HttpEntity entity2 = new HttpEntity(headers2);
		
		RestTemplate restTemplate2 = new RestTemplate();
		ResponseEntity<String> responseEntity2 = restTemplate2.exchange(kakaoLogin.getUserinfo_url() , HttpMethod.GET, entity2, String.class);
		String body2 = responseEntity2.getBody();
		
		log.info("카카오가 보낸 사용자 정보는 "+body2);
		//211111111111111111111111111111111111111111111111111111111111111111111111
		ObjectMapper objectMapperInfo = new ObjectMapper();
		Map<String, Object> kakaoUserInfo = new HashMap<>();
		try {
			kakaoUserInfo = objectMapperInfo.readValue(body2, Map.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String kakaoId = String.valueOf(kakaoUserInfo.get("id"));
		String nickname = (String) ((Map<String, Object>) kakaoUserInfo.get("properties")).get("nickname");
		
		Member member = new Member();
		member.setUid(kakaoId);
		member.setNickname(nickname);
		member.setSns(snsService.selectByName("kakao")); 
		member.setRole(roleService.selectByName("USER"));//일반 회원 가입이므로...
		
	
		//중복된 회원이 없다면, 가입을 시킨다...(즉 최초 한번은 가입을 회원 정보를 보관해놓자..)
		Member dto = memberService.selectByUid(kakaoId);
		
		if(dto == null) { //중복된 회원이 없을때만 가입
			memberService.regist(member);
			dto =  member;
		}
		
		//세션을 할당하여, 메인으로 보낸다..
		HttpSession session = request.getSession();
	    session.setAttribute("member", dto);
	    log.debug("현재 가진 권한은 "+dto.getRole().getRole_name());
		
		//스프링 시큐리티의 권한 부여를 강제적으로 처리 
		//(CustomeUserDetails 로부터 자동으로 값을 할당하는 방식이 아니라, 개발자가 수동으로 시큐리티에게 정보 주입)
		Authentication auth = new UsernamePasswordAuthenticationToken(member.getNickname(), null, Collections.singletonList(new SimpleGrantedAuthority("USER")));
		SecurityContextHolder.getContext().setAuthentication(auth);
		session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
		
		ModelAndView mav = new ModelAndView("redirect:/");
		return mav;
	}
}