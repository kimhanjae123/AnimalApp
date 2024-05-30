package com.sds.animalapp.model.shelter;

import java.util.List;

import com.sds.animalapp.domain.Shelter;
import com.sds.animalapp.domain.ShelterSelectParam;

public interface ShelterService {
	public int selectCount(String keyword); //총 레코드 수
	public List selectAll(ShelterSelectParam shelterSelectParam);
	public Shelter select(int shelter_idx);
	public void saveAll(List<Shelter> shelterList);
}