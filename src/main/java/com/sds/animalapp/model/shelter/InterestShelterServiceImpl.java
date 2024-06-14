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

	@Override
	public boolean duplicatedInterestShelter(int member_idx, int shelter_idx) {
		if(interestShelterDAO.duplicatedInterestShelter(member_idx, shelter_idx)) {
			return false;
		}
		InterestShelter interestShelter = new InterestShelter();
		interestShelter.setMember_idx(member_idx);
		interestShelter.setShelter_idx(shelter_idx);
		
		return true;
	}



}
