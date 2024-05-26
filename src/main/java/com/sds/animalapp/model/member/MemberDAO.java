package com.sds.animalapp.model.member;

import org.apache.ibatis.annotations.Mapper;

import com.sds.animalapp.domain.Member;

@Mapper
public interface MemberDAO {
    int insert(Member member); // 회원등록
    Member selectByUid(String uid); // uid에 해당하는 회원정보 가져오기
    int update(Member member); // 사용자 정보 업데이트
}
