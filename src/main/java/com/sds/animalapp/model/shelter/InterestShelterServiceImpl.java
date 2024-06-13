package com.sds.animalapp.model.shelter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.animalapp.domain.InterestShelter;

@Service
public class InterestShelterServiceImpl implements InterestShelterService{
	
	@Autowired
	private InterestShelterDAO interestShelterDAO;

	public void insertInterestShelter(InterestShelter interestShelter) {
		
	}

	public void deleteInterestShelter(int interest_shetler_idx) {
		
	}

}
