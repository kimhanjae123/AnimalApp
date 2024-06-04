package com.sds.animalapp.domain;

import lombok.Data;

@Data
public class AnimalSelectParam {
	private String keyword;// 품종 검색어
	private int startIndex;// 시작 index
	private int rowCount;// 열 개수
	private String currentSidoCode;// 선택된 시도 코드
	private String currentSignguCode;// 선택된 시군구 코드
}
