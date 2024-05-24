package com.sds.animalapp.model.member;

import com.sds.animalapp.domain.Sns;

public interface SnsService {
	public Sns selectByName(String sns_name);
}
