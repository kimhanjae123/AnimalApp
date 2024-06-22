package com.sds.animalapp.model.member;

import com.sds.animalapp.domain.Member;
import com.sds.animalapp.domain.MemberDetail;

public interface MemberService {

	public void regist(Member member);
    public Member selectByUid(String uid);
    public Member getMemberByIdx(int member_idx);
    public void updateMemberDetail(MemberDetail memberDetail);  // 사용자 정보 업데이트
    void update(Member member);
    public int getMemberDetailByMemberIdx(int member_idx);
    public void insertMemberDetail(MemberDetail memberDetail);
    public void updateProfile(String imgUrl, int member_idx);
}
