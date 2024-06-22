package com.sds.animalapp.model.member;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sds.animalapp.domain.Member;

@Mapper
public interface MemberDAO {
    int insert(Member member); // 회원등록
    Member selectByUid(String uid); // uid에 해당하는 회원정보 가져오기
    Member selectByIdx(int member_idx); // uid에 해당하는 회원정보 가져오기
    int update(Member member); // 사용자 정보 업데이트
    void updateProfileImageUrl(@Param("imgUrl") String imgUrl,@Param("member_idx") int member_idx);//프로필사진 경로 혹은 링크 변경
}
