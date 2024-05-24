package com.sds.animalapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AnimalController {

	@GetMapping("/animal/list")
	public String animalList() {
		return "animal/list"; // "animal/list.html" 파일을 가리킵니다.
	}

	@GetMapping("/animal/detail")
	public String animalDetail() {
		return "animal/detail"; // "animal/detail.html" 파일을 가리킵니다.
	}
}
