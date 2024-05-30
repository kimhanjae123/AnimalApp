package com.sds.animalapp.model.shelter;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.animalapp.domain.Shelter;
import com.sds.animalapp.domain.ShelterSelectParam;

@Service
public class ShelterServiceImpl implements ShelterService{

	@Autowired
	private ShelterDAO shelterDAO;
	
	
	public int selectCount(String keyword) {
		return shelterDAO.selectCount(keyword);
	}
	
	public List selectAll(ShelterSelectParam shelterSelectParam) {
		return shelterDAO.selectAll(shelterSelectParam);
	}

	

	public Shelter select(int shelter_idx) {
		return shelterDAO.select(shelter_idx);
	}

	public void insert(List<Shelter> shelterList) {
		shelterDAO.insert(shelterList);
		
	}

	@Override
	public void delete(List<Shelter> shelterList) {
		shelterDAO.delete(shelterList);
	}

}