package com.sds.animalapp.domain;

import lombok.Data;

@Data
public class InterestShelter {
	
	private int interest_shelter_idx;
	private int member_idx;
	private int shelter_idx;
	
	// 추가
	private String orgNm; 
	private String careNm;
}
