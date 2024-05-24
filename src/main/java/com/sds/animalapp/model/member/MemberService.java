package com.sds.animalapp.model.member;

import com.sds.animalapp.domain.Member;

public interface MemberService {

	public void regist(Member member);
	public Member selectByUid(String uid);//uid에 해당하는 회원정보 가져오기
}
