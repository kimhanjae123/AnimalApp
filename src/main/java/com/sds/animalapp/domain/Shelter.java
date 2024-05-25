package com.sds.animalapp.domain;

import lombok.Data;

@Data
public class Shelter {
	
	public int shelter_idx;
	
	//1건의 보호소 정보를 불러왔을 떄 사용될 변수들
	private String orgNm;
	private String care_nm;
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
}