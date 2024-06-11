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

	public int selectCount(AnimalSelectParam animalSelectParam) {
		return animalDAO.selectCount(animalSelectParam);
	}

	public List selectAll(AnimalSelectParam animalSelectParam) {
		return animalDAO.selectAll(animalSelectParam);
	}

	public Animal select(int animal_idx) {
		return animalDAO.select(animal_idx);
	}

	public List selectPreview() {

		return animalDAO.selectPreview();
	}

	@Override
	public int countRegistMember(int animal_idx) {
		return animalDAO.countRegistMember(animal_idx);
	}
}