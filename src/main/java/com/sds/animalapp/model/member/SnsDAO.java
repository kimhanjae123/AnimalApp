package com.sds.animalapp.model.member;

import org.apache.ibatis.annotations.Mapper;

import com.sds.animalapp.domain.Sns;

@Mapper
public interface SnsDAO {
	
	public Sns selectByName(String sns_name);
}
