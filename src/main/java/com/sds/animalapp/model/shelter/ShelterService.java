package com.sds.animalapp.model.shelter;

import java.util.List;
import java.util.Map;

import com.sds.animalapp.domain.Shelter;
import com.sds.animalapp.domain.ShelterSelectParam;

public interface ShelterService {
	public int selectCount(ShelterSelectParam shelterSelectParam); //총 레코드 수
	public List<Shelter> getAllRecord();
	public List selectAll(ShelterSelectParam shelterSelectParam);
	public Shelter select(int shelter_idx);
	public void insert(List<Shelter> shelterAllList);
	public void delete(List<Shelter> shelterAllList);
	public void mapSigngu(List<Shelter> allShelterList);//시군구 코드 컬럼에 매핑하는 메서드
	public int findShelterIdxByCareNm(String careNm);
}