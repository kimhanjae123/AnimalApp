package com.sds.animalapp.model.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sds.animalapp.domain.Member;
import com.sds.animalapp.domain.MemberDetail;
import com.sds.animalapp.domain.Role;
import com.sds.animalapp.domain.Sns;
import com.sds.animalapp.exception.MemberException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final SnsDAO snsDAO;

    private final RoleDAO roleDAO;

    private final MemberDAO memberDAO;

    private final MemberDetailDAO memberDetailDAO;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void regist(Member member) throws MemberException {
        // 홈페이지 회원의 sns 정보 가져오기
        Sns sns = snsDAO.selectByName(member.getSns().getSns_name());
        member.setSns(sns); // sns_idx가 채워진 DTO를 다시 Member DTO에 대입

        // 회원의 권한 가져오기
        Role role = roleDAO.selectByName(member.getRole().getRole_name());
        member.setRole(role); // role_idx가 채워진 DTO를 다시 Member DTO에 대입

        int result = memberDAO.insert(member);

        if (result < 1) {
            throw new MemberException("회원 등록 실패");
        }

        // 현재 기준 회원 상세정보를 입력처리해야 되는 회원은 홈페이지 회원이므로, 조건문으로 처리..
        if (sns.getSns_name().equals("hompage")) {
            // 회원 상세 정보 등록
            MemberDetail memberDetail = member.getMemberDetail();
            memberDetail.setMember(member);
            memberDetail.setPassword(bCryptPasswordEncoder.encode(memberDetail.getPassword()));

            // 비밀번호 암호화 처리
            result = memberDetailDAO.insert(memberDetail); // 회원 상세 정보 등록
            if (result < 1) {
                throw new MemberException("회원 추가정보 등록 실패");
            }
        }
    }

    @Override
    public Member selectByUid(String uid) {
        return memberDAO.selectByUid(uid);
    }

    @Override
    public void updateMemberDetail(MemberDetail memberDetail) {
        memberDetailDAO.update(memberDetail); // MemberDetail 업데이트 메서드 호출
    }

    @Override
    public void update(Member member) {
        memberDAO.update(member); // Member 업데이트 메서드 호출
    }

	@Override
	public int getMemberDetailByMemberIdx(int member_idx) {
		return memberDetailDAO.countByMemberIdx(member_idx);
	}

	@Override
	public void insertMemberDetail(MemberDetail memberDetail) {
		memberDetailDAO.insert(memberDetail);
	}

	@Override
	public void updateProfile(String imgUrl, int member_idx) {
		memberDAO.updateProfileImageUrl(imgUrl, member_idx);
	}

	@Override
	public Member getMemberByIdx(int member_idx) {
		return memberDAO.selectByIdx(member_idx);
	}
}

