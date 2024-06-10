package com.sds.animalapp.model.animal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.animalapp.domain.InterestAnimal;

@Service
public class InterestAnimalServiceImpl implements InterestAnimalService {

	@Autowired
	private InterestAnimalDAO interestAnimalDAO;

	@Override
	public void addInterestAnimal(InterestAnimal interestAnimal) {
		interestAnimalDAO.addInterestAnimal(interestAnimal);
	}

	@Override
	public void deleteInterestAnimal(String interestAnimalIdx) {
		interestAnimalDAO.deleteInterestAnimal(interestAnimalIdx);
	}
}
