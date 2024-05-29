package com.sds.animalapp.model.shelter;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.sds.animalapp.domain.Shelter;

@Mapper
public interface ShelterDAO {

	public int selectCount(String keyword); //총 레코드 수
	public List selectAll(Map map);
	public Shelter select(int shelter_idx);
	public void saveAll(List<Shelter> shelterList);
	
}