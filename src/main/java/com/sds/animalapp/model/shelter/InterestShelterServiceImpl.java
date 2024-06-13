package com.sds.animalapp.model.shelter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.animalapp.domain.InterestShelter;

@Service
public class InterestShelterServiceImpl implements InterestShelterService{
	
	@Autowired
	private InterestShelterDAO interestShelterDAO;

	public void insertInterestShelter(InterestShelter interestShelter) {
		interestShelterDAO.insertInterestShelter(interestShelter);			
	}

	public void deleteInterestShelter(int interest_shetler_idx) {
		interestShelterDAO.deleteInterestShelter(interest_shetler_idx);
	}

	public boolean duplicatedInterestShelter(int shelter_idx, int member_idx) {
		return interestShelterDAO.duplicatedInterestShelter(shelter_idx, member_idx) != null;
	}


}
