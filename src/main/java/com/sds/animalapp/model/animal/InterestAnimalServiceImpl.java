package com.sds.animalapp.model.animal;

import java.util.List;

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

	@Override
	public List<InterestAnimal> getAllInterests() {
		return interestAnimalDAO.findAll();
	}

	@Override
	public List<InterestAnimal> getInterestByMemberIdx(int member_idx) {
		return interestAnimalDAO.findByMemberIdx(member_idx);
	}

	@Override
	public InterestAnimal findById(int interest_animal_idx) {
		return interestAnimalDAO.findInterestById(interest_animal_idx);
	}

	@Override
	public InterestAnimal findInterestByAnimalIdxAndMemberIdx(int animal_idx, int member_idx) {
		return interestAnimalDAO.findInterestByAnimalIdxAndMemberIdx(animal_idx, member_idx);
	}

	@Override
	public int getInterestRecordNum(int member_idx, int animal_idx) {
		return interestAnimalDAO.selectCount(member_idx, animal_idx);
	}
}
