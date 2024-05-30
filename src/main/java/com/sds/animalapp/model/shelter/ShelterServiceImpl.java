package com.sds.animalapp.model.shelter;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.animalapp.domain.Shelter;

@Service
public class ShelterServiceImpl implements ShelterService{

	@Autowired
	private ShelterDAO shelterDAO;
	
	
	public int selectCount() {
		return shelterDAO.selectCount();
	}
	
	public List selectAll(Map map) {
		return shelterDAO.selectAll(map);
	}

	

	public Shelter select(int shelter_idx) {
		return shelterDAO.select(shelter_idx);
	}

	public void insert(List<Shelter> shelterList) {
		shelterDAO.insert(shelterList);
		
	}

}