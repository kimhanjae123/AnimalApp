package com.sds.animalapp.model.member;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sds.animalapp.controller.MemberController;
import com.sds.animalapp.domain.Member;
import com.sds.animalapp.sns.KaKaoLogin;
import com.sds.animalapp.sns.KaKaoOAuthToken;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KakaoLoginServiceImpl implements KakaoLoginService {

	@Autowired
	private KaKaoLogin kakaoLogin;

	@Autowired
	private RoleService roleService;

	@Autowired
	private SnsService snsService;

	@Autowired
	private MemberService memberService;

	@Override
	public KaKaoOAuthToken getKakaoAccessToken(HttpServletRequest request) {
		String code = request.getParameter("code");

		log.info("카카오가 보내 임시 코드는 " + code);

		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("code", code);
		params.add("client_id", kakaoLogin.getClient_id());
		params.add("redirect_uri", kakaoLogin.getRedirect_uri());
		params.add("grant_type", kakaoLogin.getGrant_type());

		// 서버에게 요청 본문(body)의 데이터 유형에 대한 정보를 제공한다
		// "application/x-www-form-urlencoded"는 HTML 폼 데이터를 인코딩하는 방식이다.
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/x-www-form-urlencoded");

		log.info("headers: " + headers);

		// HttpEntity는 Spring에서 HTTP 요청/응답을 표현하는 데 사용되는 클래스
		HttpEntity entity = new HttpEntity(params, headers);

		log.info("entity: " + entity);

		// RestTemplate을 사용하여 Kakao 서버에 POST 요청을 보내고, 응답으로 받은 액세스 토큰을 포함한 본문 데이터를
		// ResponseEntity<String> 객체로 저장하는 역할
		// RestTemplate은 Spring에서 제공하는 클라이언트 측 HTTP 접근 클래스입니다. REST 기반 서비스와 통신하기 위해
		// 사용된다.
		// exchange 메서드는 HTTP 요청을 전송하고 응답을 받는 메서드
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.exchange(kakaoLogin.getToken_request_url(),
				HttpMethod.POST, entity, String.class);

		// 카카오 서버에서 받은 응답인 responseEntity에서 본문을 추출
		String body = responseEntity.getBody();
		log.info("카카오가 보낸 토큰을 포함한 응답정보는 " + body);

		ObjectMapper objectMapper = new ObjectMapper();

		KaKaoOAuthToken oAuthToken = null;
		try {
			// readValue 메서드는 JSON 형태의 문자열(body)을 Java 객체(KaKaoOAuthToken.class)로 매핑한다.
			oAuthToken = objectMapper.readValue(body, KaKaoOAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return oAuthToken;
	}

	public Member registMember(KaKaoOAuthToken oAuthToken) {
		// Kakao 사용자 정보를 가져오기 위한 HTTP 요청 헤더
		HttpHeaders userInfoHeader = new HttpHeaders();
		userInfoHeader.add("Authorization", "Bearer " + oAuthToken.getAccess_token());

		// 새로운 헤더 정보만을 포함하는 entity 객체
		HttpEntity userInfoEntity = new HttpEntity(userInfoHeader);

		RestTemplate restTemplate2 = new RestTemplate();
		ResponseEntity<String> userInfoResponseEntity = restTemplate2.exchange(kakaoLogin.getUserinfo_url(),
				HttpMethod.GET, userInfoEntity, String.class);
		// 사용자 정보 본문 추출
		String userInfoBody = userInfoResponseEntity.getBody();

		log.info("카카오가 보낸 사용자 정보는 " + userInfoBody);
		// 사용자 정보 문자열 매핑
		ObjectMapper objectMapperInfo = new ObjectMapper();
		Map<String, Object> kakaoUserInfo = new HashMap<String, Object>();
		try {
			kakaoUserInfo = objectMapperInfo.readValue(userInfoBody, Map.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		String kakaoId = String.valueOf(kakaoUserInfo.get("id"));
		String nickname = (String) ((Map<String, Object>) kakaoUserInfo.get("properties")).get("nickname");
		String profileImage = (String) ((Map<String, Object>) kakaoUserInfo.get("properties")).get("profile_image");

		// Member클래스에 정보 매핑
		Member member = new Member();
		member.setUid(kakaoId);
		member.setNickname(nickname);
		member.setSns(snsService.selectByName("kakao"));
		member.setRole(roleService.selectByName("USER"));// 권한설정
		member.setProfile_image_url(profileImage);

		log.info(profileImage);

		// 신규 회원일 경우 DB에 등록
		Member dto = memberService.selectByUid(kakaoId);

		if (dto == null) {
			memberService.regist(member);
			dto = member;
		}
		return dto;
	}

	@Override
	public void authenticateUser(Member member, HttpServletRequest request) {
		// Member 객체를 HTTP 세션에 저장
		HttpSession session = request.getSession();
		session.setAttribute("member", member);

		log.debug("현재 가진 권한은 " + member.getRole().getRole_name());

		// Spring Security에서 사용자의 인증 정보를 나타내는 객체
		Authentication auth = new UsernamePasswordAuthenticationToken(member.getNickname(), null,
				Collections.singletonList(new SimpleGrantedAuthority("USER")));
		// Spring Security에서 인증 정보를 유지하는 객체
		SecurityContextHolder.getContext().setAuthentication(auth);
		// 세션에 저장
		session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

	}

}
