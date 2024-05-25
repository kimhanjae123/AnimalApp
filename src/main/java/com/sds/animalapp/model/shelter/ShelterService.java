package com.sds.animalapp.model.shelter;

import java.util.List;
import java.util.Map;

import com.sds.animalapp.domain.Shelter;

public interface ShelterService {
	public int selectCount(); //총 레코드 수
	public List selectAll(Map map);
	public Shelter select(int shelter_idx);
}