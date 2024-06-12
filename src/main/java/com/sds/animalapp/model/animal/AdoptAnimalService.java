package com.sds.animalapp.model.animal;

import com.sds.animalapp.domain.AdoptAnimal;

public interface AdoptAnimalService {

	// 입양신청 동물 등록
	public void addAdoptAnimal(AdoptAnimal adoptAnimal);

	// 입양신청 동물 삭제
	public void deleteAdoptAnimal(int adopt_animal_idx);

	// 입양신청 동물 중복 확인
	public boolean checkDuplicateAdoptAnimal(AdoptAnimal adoptAnimal);

}
