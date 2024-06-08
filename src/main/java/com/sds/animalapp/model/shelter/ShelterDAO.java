package com.sds.animalapp.model.shelter;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.sds.animalapp.domain.Shelter;
import com.sds.animalapp.domain.ShelterSelectParam;

@Mapper
public interface ShelterDAO {
	public int selectCount(); //총 레코드 수
	public List selectAll(Map map);

	public int selectCount(String keyword); //총 레코드 수
	public List selectAll(ShelterSelectParam shelterSelectParam);
	public Shelter select(int shelter_idx);
	public void insert(List<Shelter> shelterList);
	public void delete(List<Shelter> shelterList);
	
}