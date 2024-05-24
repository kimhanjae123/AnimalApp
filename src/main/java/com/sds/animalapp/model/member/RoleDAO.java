package com.sds.animalapp.model.member;

import org.apache.ibatis.annotations.Mapper;

import com.sds.animalapp.domain.Role;

@Mapper
public interface RoleDAO {
	public Role selectByName(String role_name);
}
