package com.sds.animalapp.model.member;

import org.apache.ibatis.annotations.Mapper;

import com.sds.animalapp.domain.MemberDetail;

@Mapper
public interface MemberDetailDAO {
	
	public int insert(MemberDetail memberDetail);
}
