package com.sds.animalapp.model.animal;

import java.util.List;

import com.sds.animalapp.domain.Animal;
import com.sds.animalapp.domain.AnimalSelectParam;

public interface AnimalService {
	public int selectCount(String keyword); // 총 레코드 수

	public List selectAll(AnimalSelectParam animalSelectParam); // 검색어에 매칭되는 모든 필터링 된 동물 조회

	public Animal select(int animal_idx); // 동물 상세 조회
}