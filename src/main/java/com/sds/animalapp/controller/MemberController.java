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
import com.sds.animalapp.domain.Sns;
import com.sds.animalapp.model.member.MemberService;
import com.sds.animalapp.model.member.RoleService;
import com.sds.animalapp.model.member.SnsService;
import com.sds.animalapp.sns.KaKaoLogin;
import com.sds.animalapp.sns.KaKaoOAuthToken;
import com.sds.animalapp.sns.NaverLogin;
import com.sds.animalapp.sns.NaverOAuthToken;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberController {
    
    @Autowired
    private NaverLogin naverLogin;
    
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
    
    @GetMapping("/member/sns/naver/callback")
    public ModelAndView naverCallback(HttpServletRequest request, HttpSession session) {
        String code = request.getParameter("code");

        String token_url = naverLogin.getToken_request_url();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", naverLogin.getClient_id());
        params.add("client_secret", naverLogin.getClient_secret());
        params.add("redirect_uri", naverLogin.getRedirect_uri());
        params.add("grant_type", naverLogin.getGrant_type());
        params.add("state", naverLogin.getState());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(token_url, HttpMethod.POST, entity, String.class);

        String body = responseEntity.getBody();
        log.info("네이버가 보낸 인증 완료 정보는 " + body);

        ObjectMapper objectMapper = new ObjectMapper();
        NaverOAuthToken oAuthToken = null;

        try {
            oAuthToken = objectMapper.readValue(body, NaverOAuthToken.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        String userinfo_url = naverLogin.getUserinfo_url();
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
        HttpEntity<?> entity2 = new HttpEntity<>(headers2);

        ResponseEntity<String> userEntity = restTemplate.exchange(userinfo_url, HttpMethod.GET, entity2, String.class);
        String userBody = userEntity.getBody();
        log.info(userBody);

        ObjectMapper objectMapper2 = new ObjectMapper();
        HashMap<String, Object> userMap = null;

        try {
            userMap = objectMapper2.readValue(userBody, HashMap.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Map<String, Object> response = (Map<String, Object>) userMap.get("response");
        String id = (String) response.get("id");
        String email = (String) response.get("email");
        String name = (String) response.get("name");

        log.debug("id = " + id);
        log.debug("email = " + email);
        log.debug("name = " + name);

        Member member = new Member();
        member.setUid(id);
        member.setNickname(name);
        member.setEmail(email);

        Sns naverSns = snsService.selectByName("naver");
        if (naverSns == null) {
            throw new RuntimeException("SNS 'naver' not found in database.");
        }
        member.setSns(naverSns);
        member.setRole(roleService.selectByName("USER"));

        Member dto = memberService.selectByUid(id);

        if (dto == null) {
            memberService.regist(member);
            dto = member;
        }

        session.setAttribute("member", dto);
        log.debug("현재 가진 권한은 " + dto.getRole().getRole_name());

        Authentication auth = new UsernamePasswordAuthenticationToken(member.getNickname(), null, Collections.singletonList(new SimpleGrantedAuthority("USER")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        return new ModelAndView("redirect:/member/mypage");
    }
    
    @GetMapping("/member/sns/kakao/callback")
    public ModelAndView kakaoCallback(HttpServletRequest request) {
        String code = request.getParameter("code");
        log.info("카카오가 보내 임시 코드는 " + code);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", kakaoLogin.getClient_id());
        params.add("redirect_uri", kakaoLogin.getRedirect_uri());
        params.add("grant_type", kakaoLogin.getGrant_type());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(kakaoLogin.getToken_request_url(), HttpMethod.POST, entity, String.class);
        String body = responseEntity.getBody();
        log.info("카카오가 보낸 토큰을 포함한 응답정보는 " + body);

        ObjectMapper objectMapper = new ObjectMapper();
        KaKaoOAuthToken oAuthToken = null;
        try {
            oAuthToken = objectMapper.readValue(body, KaKaoOAuthToken.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
        HttpEntity<?> entity2 = new HttpEntity<>(headers2);

        ResponseEntity<String> responseEntity2 = restTemplate.exchange(kakaoLogin.getUserinfo_url(), HttpMethod.GET, entity2, String.class);
        String body2 = responseEntity2.getBody();
        log.info("사용자 정보는 " + body2);

        ObjectMapper objectMapper2 = new ObjectMapper();
        HashMap<String, Object> userMap = null;

        try {
            userMap = objectMapper2.readValue(body2, HashMap.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Map<String, Object> kakaoAccount = (Map<String, Object>) userMap.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        String kakaoId = String.valueOf(userMap.get("id"));
        String nickname = (String) profile.get("nickname");

        log.debug("카카오 ID: " + kakaoId);
        log.debug("닉네임: " + nickname);

        Member member = new Member();
        member.setUid(kakaoId);
        member.setNickname(nickname);

        Sns kakaoSns = snsService.selectByName("kakao");
        if (kakaoSns == null) {
            throw new RuntimeException("SNS 'kakao' not found in database.");
        }
        member.setSns(kakaoSns);
        member.setRole(roleService.selectByName("USER"));

        Member dto = memberService.selectByUid(kakaoId);

        if (dto == null) {
            memberService.regist(member);
            dto = member;
        }

        HttpSession session = request.getSession();
        session.setAttribute("member", dto);
        log.debug("현재 가진 권한은 " + dto.getRole().getRole_name());

        Authentication auth = new UsernamePasswordAuthenticationToken(member.getNickname(), null, Collections.singletonList(new SimpleGrantedAuthority("USER")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        return new ModelAndView("redirect:/member/mypage");
    }
}
