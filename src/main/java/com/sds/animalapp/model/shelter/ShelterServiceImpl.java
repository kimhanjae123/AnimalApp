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
	
	@Autowired
	private ShelterApiService shelterApiService;
	
	public int selectCount() {
		return 0;
	}
	
	public List selectAll(Map map) {
		
		
		return null;
	}

	

	public Shelter select(int shelter_idx) {
		return null;
	}

}