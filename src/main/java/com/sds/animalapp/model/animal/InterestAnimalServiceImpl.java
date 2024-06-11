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
		// 중복 체크
		if (!checkDuplicateInterestAnimal(interestAnimal)) {
			interestAnimalDAO.addInterestAnimal(interestAnimal);
		} else {
			throw new IllegalArgumentException("이미 등록된 관심 동물입니다.");
		}
	}

	@Override
	public void deleteInterestAnimal(int interest_animal_idx) {
		interestAnimalDAO.deleteInterestAnimal(interest_animal_idx);
	}

	@Override
	public boolean checkDuplicateInterestAnimal(InterestAnimal interestAnimal) {
		int count = interestAnimalDAO.checkDuplicateInterestAnimal(interestAnimal);
		return count > 0;
	}
}
