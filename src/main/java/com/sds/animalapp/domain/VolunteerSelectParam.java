package com.sds.animalapp.domain;

import lombok.Data;

@Data
public class VolunteerSelectParam {
	private String keyword;//보호센터명 검색어
	private int startIndex;//시작 index
	private int rowCount;//열 개수
}
