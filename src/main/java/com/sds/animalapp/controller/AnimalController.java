package com.sds.animalapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sds.animalapp.common.Pager;
import com.sds.animalapp.domain.Animal;
import com.sds.animalapp.domain.AnimalSelectParam;
import com.sds.animalapp.domain.Sido;
import com.sds.animalapp.domain.Signgu;
import com.sds.animalapp.model.animal.AnimalApiService;
import com.sds.animalapp.model.animal.AnimalService;
import com.sds.animalapp.model.shelter.SidoService;
import com.sds.animalapp.model.shelter.SignguService;

@Controller
public class AnimalController {

	@Autowired
	private AnimalApiService animalApiService;

	@Autowired
	private AnimalService animalService;

	@Autowired
	private SidoService sidoService;

	@Autowired
	private SignguService signguService;

	@GetMapping("/animal/list")
	public String getAnimal(Animal animal, Model model,
			@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
			@RequestParam(value = "keyword", defaultValue = "") String keyword,
			@RequestParam(value = "currentSidoCode", defaultValue = "00") String currentSidoCode,
			@RequestParam(value = "currentSignguCode", defaultValue = "00") String currentSignguCode) throws Exception {

		Pager pager = new Pager();
		pager.init(animalService.selectCount(keyword), currentPage);

		List<Sido> sidoList = sidoService.selectAll();

		// List<Signgu> signguList = signguService.selectAll(currentSidoCode);

		// animal DB 테이블이 시도 테이블과 시군구 테이블을 참조하도록 수정한 후 검색 다시 구현
		AnimalSelectParam animalSelectParam = new AnimalSelectParam();
		animalSelectParam.setKeyword(keyword);
		animalSelectParam.setStartIndex(pager.getStartIndex());
		animalSelectParam.setRowCount(pager.getPageSize());
		animalSelectParam.setCurrentSidoCode(currentSidoCode);
		animalSelectParam.setCurrentSignguCode(currentSignguCode);

		List animalList = animalService.selectAll(animalSelectParam);

		model.addAttribute("pager", pager);
		model.addAttribute("animalList", animalList);
		model.addAttribute("sidoList", sidoList);
		model.addAttribute("keyword", keyword);
		model.addAttribute("currentSidoCode", currentSidoCode);
		model.addAttribute("currentSignguCode", currentSignguCode);

		return "animal/list";
	}

	// 세부창
	@GetMapping("/animal/detail")
	public String getDetail(Model model, @RequestParam(value = "id") int animal_idx) {
		Animal animal = animalService.select(animal_idx);

		model.addAttribute("detail", animal);

		return "animal/detail";
	}

	@GetMapping("/animal/select/signgu")
	@ResponseBody
	public List<Signgu> getSigngu(
			@RequestParam(value = "currentSidoCode", defaultValue = "00") String currentSidoCode) {

		List<Signgu> signguList = signguService.selectAll(currentSidoCode);

		return signguList;
	}
}
