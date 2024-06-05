package com.sds.animalapp.model.animal;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sds.animalapp.domain.Animal;
import com.sds.animalapp.domain.AnimalSelectParam;

@Mapper
public interface AnimalDAO {
	public int selectCount(String keyword); // 총 레코드 수

	public List selectAll(AnimalSelectParam animalSelectParam); // 검색어에 매칭되는 모든 필터링 된 동물 조회

<<<<<<< HEAD
	public Animal select(int animal_idx); // 동물 상세 조회
=======
	public Animal select(int animal_idx);
	
	public List selectPreview();
>>>>>>> e0493a208ce51209ace2bd8e5472f99f2f11c2d4

}