package com.sds.animalapp.model.animal;

import com.sds.animalapp.domain.InterestAnimal;
import com.sds.animalapp.domain.Member;

public interface InterestAnimalService {

	// 관심 동물 등록
	public void addInterestAnimal(InterestAnimal interestAnimal);

	// 관심 동물 삭제
	public void deleteInterestAnimal(String interestAnimalIdx);
}
