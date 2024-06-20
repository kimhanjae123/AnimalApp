package com.sds.animalapp.model.member;

import org.apache.ibatis.annotations.Mapper;

import com.sds.animalapp.domain.MemberDetail;

@Mapper
public interface MemberDetailDAO {
    public int insert(MemberDetail memberDetail); // 회원 상세 정보 등록
    public MemberDetail selectByMemberIdx(int member_idx); // member_idx에 해당하는 상세 정보 가져오기
    public int update(MemberDetail memberDetail); // 회원 상세 정보 업데이트
    public int countByMemberIdx(int member_idx);
}