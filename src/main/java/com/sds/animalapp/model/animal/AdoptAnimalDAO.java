package com.sds.animalapp.model.animal;

import org.apache.ibatis.annotations.Mapper;

import com.sds.animalapp.domain.AdoptAnimal;

@Mapper
public interface AdoptAnimalDAO {
	// 입양신청 동물 등록
	public void addAdoptAnimal(AdoptAnimal adoptAnimal);

	// 입양신청 동물 삭제
	public void deleteAdoptAnimal(int adoptAnimalIdx);

	// 입양신청 동물 중복 확인
	public int checkDuplicateAdoptAnimal(AdoptAnimal adoptAnimal);
}
