package com.sds.animalapp.domain;

import lombok.Data;

@Data
public class AnimalSelectParam {
	private String keyword; // 품종 검색어
	private int startIndex; // 시작 index
	private int rowCount; // 열 개수
	private String age; // 나이
	private String region; // 지역
	private String care; // 관할기관
	private String sex; // 성별
	private String status; // 상태
}
