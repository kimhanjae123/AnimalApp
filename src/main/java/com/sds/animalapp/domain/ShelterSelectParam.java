package com.sds.animalapp.domain;

import lombok.Data;

@Data
public class ShelterSelectParam {//보호소 페이지의 검색을 위한 Parameter class 입니다.
	private String keyword;//보호센터명 검색어
	private int startIndex;//시작 index
	private int rowCount;//열 개수
	private String currentSidoCode;//선택된 시도 코드
	private String currentSignguCode;//선택된 시군구 코드
}
