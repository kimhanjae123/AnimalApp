package com.sds.animalapp.model.shelter;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sds.animalapp.domain.Shelter;
import com.sds.animalapp.domain.ShelterSelectParam;
import com.sds.animalapp.domain.ShelterSidoMappingParam;

@Mapper
public interface ShelterDAO {
	public int selectCount(ShelterSelectParam shelterSelectParam); //총 레코드 수
	public List selectAll(ShelterSelectParam shelterSelectParam);
	public List getAllRecord();
	public Shelter select(int shelter_idx);
	public void insert(List<Shelter> shelterAllList);
	public void updateSidoCode(ShelterSidoMappingParam shelterSidoMappingParam);
	public void updateSignguCode(ShelterSidoMappingParam shelterSidoMappingParam);
	public void delete(List<Shelter> shelterAllList);
	
	public int findShelterIdxByCareNm(String careNm);
}