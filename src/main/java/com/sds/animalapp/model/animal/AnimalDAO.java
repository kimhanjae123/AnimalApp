package com.sds.animalapp.model.animal;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sds.animalapp.domain.Animal;
import com.sds.animalapp.domain.AnimalSelectParam;

@Mapper
public interface AnimalDAO {
	public int selectCount(AnimalSelectParam animalSelectParam); // 필터링에 따른 총 레코드 수

	public List selectAll(AnimalSelectParam animalSelectParam); // 필터링에 매칭되는 모든 필터링 된 동물 조회

	public Animal select(int animal_idx); // 동물 상세 조회

	public List selectPreview(); // 미리보기

	// 한 동물에 대한 입양신청 인원 조회
	public int countRegistMember(int animal_idx);

}