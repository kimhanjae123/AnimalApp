package com.sds.animalapp.model.member;

import org.apache.ibatis.annotations.Mapper;

import com.sds.animalapp.domain.Member;


@Mapper
public interface MemberDAO {
	public int insert(Member member); //회원등록
	public Member selectByUid(String uid);//uid에 해당하는 회원정보 가져오기
	
}
