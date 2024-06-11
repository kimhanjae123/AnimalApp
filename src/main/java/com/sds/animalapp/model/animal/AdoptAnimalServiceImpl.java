package com.sds.animalapp.model.animal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.animalapp.domain.AdoptAnimal;

@Service
public class AdoptAnimalServiceImpl implements AdoptAnimalService {

	@Autowired
	private AdoptAnimalDAO adoptAnimalDAO;

	@Override
	public void addAdoptAnimal(AdoptAnimal adoptAnimal) {
		// 중복 체크
		if (!checkDuplicateAdoptAnimal(adoptAnimal)) {
			adoptAnimalDAO.addAdoptAnimal(adoptAnimal);
		} else {
			throw new IllegalArgumentException("이미 입양 신청된 관심 동물입니다.");
		}
	}

	@Override
	public void deleteAdoptAnimal(int adoptAnimalIdx) {
		adoptAnimalDAO.deleteAdoptAnimal(adoptAnimalIdx);
	}

	@Override
	public boolean checkDuplicateAdoptAnimal(AdoptAnimal adoptAnimal) {
		int count = adoptAnimalDAO.checkDuplicateAdoptAnimal(adoptAnimal);
		return count > 0;
	}

}
