package com.sds.animalapp.domain;

import lombok.Data;

@Data
public class Shelter {
	
	private int shelter_idx;
	
	//1건의 보호소 정보를 불러왔을 떄 사용될 변수들
	private String orgNm;
	private String careNm;
	private String careAddr;
	private String divisionNm;
	private double lat;
	private double lng;
	private String dsignationDate;
	private String closeDay;
	private String weekOprStime;
	private String weekOprEtime;
	private String careTel;
	private String dataStdDt;
	private String orgCd;
	private String uprCd;
}