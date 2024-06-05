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

<<<<<<< HEAD
=======
	public void saveAll(List<Animal> animalList) {

		animalDAO.saveAll(animalList);

	}

	public List selectPreview() {
		
		return animalDAO.selectPreview();
	}
>>>>>>> e0493a208ce51209ace2bd8e5472f99f2f11c2d4
}