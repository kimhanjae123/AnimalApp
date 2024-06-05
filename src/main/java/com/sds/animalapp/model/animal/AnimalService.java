package com.sds.animalapp.model.animal;

import java.util.List;

import com.sds.animalapp.domain.Animal;
import com.sds.animalapp.domain.AnimalSelectParam;

public interface AnimalService {
	public int selectCount(String keyword); // 총 레코드 수

	public List selectAll(AnimalSelectParam animalSelectParam);

	public Animal select(int animal_idx);
	
	public List selectPreview();

	public void saveAll(List<Animal> animalList);
}