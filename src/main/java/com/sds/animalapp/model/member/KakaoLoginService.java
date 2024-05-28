package com.sds.animalapp.model.member;

import com.sds.animalapp.domain.Member;
import com.sds.animalapp.sns.KaKaoOAuthToken;

import jakarta.servlet.http.HttpServletRequest;

public interface KakaoLoginService {
	public KaKaoOAuthToken getKakaoAccessToken(HttpServletRequest request);
	public Member registMember(KaKaoOAuthToken oAuthToken);
	public void authenticateUser(Member member, HttpServletRequest request);
}
