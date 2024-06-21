package com.sds.animalapp.domain;

import lombok.Data;

@Data
public class Member {
	private int member_idx;
	private String uid;
	private String nickname;
	private String email;
	private String profile_image_url;
	private MemberDetail memberDetail;
	private Sns sns; //has a 관계로 erd 상의 부모를 보유
	private Role role; //has a 관계로 erd 상의 부모를 보유
	
	private String profileImageUrl;

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}