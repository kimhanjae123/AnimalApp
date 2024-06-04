package com.sds.animalapp.model.animal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.animalapp.domain.Animal;
import com.sds.animalapp.domain.AnimalSelectParam;

@Service
public class AnimalServiceImpl implements AnimalService {

	@Autowired
	private AnimalDAO animalDAO;

	public int selectCount(String keyword) {
		return animalDAO.selectCount(keyword);
	}

	public List selectAll(AnimalSelectParam animalSelectParam) {
		return animalDAO.selectAll(animalSelectParam);
	}

	public Animal select(int animal_idx) {
		return animalDAO.select(animal_idx);
	}

	public void saveAll(List<Animal> animalList) {

		animalDAO.saveAll(animalList);

	}
}