package com.sds.animalapp.model.member;

import com.sds.animalapp.domain.Role;

public interface RoleService {
	public Role selectByName(String role_name);	
}
