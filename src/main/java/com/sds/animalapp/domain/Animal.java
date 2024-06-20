package com.sds.animalapp.domain;

import lombok.Data;

@Data
public class Animal {

	private int animal_idx;
	private Integer shelter_idx;

	// 하나의 유기동물을 불러왔을 때 사용할 변수
	private String desertionNo; // 유기번호
	private String filename = ""; // 썸네일 이미지 경로
	private String happenDt; // 접수일
	private String happenPlace; // 발견장소
	private String kindCd;// 품종
	private String colorCd;// 색상
	private String age;// 나이
	private String weight;// 체중
	private String noticeNo;// 공고번호
	private String noticeSdt;// 공고시작일
	private String noticeEdt;// 공고종료일
	private String popfile;// image
	private String processState;// 상태
	private String sexCd;// 성별
	private String neuterYn;// 중성화여부
	private String specialMark;// 특징
	private String careNm;// 보호소 이름
	private String careTel;// 보호소 전화번호
	private String careAddr;// 보호장소
	private String orgNm;// 관할기관
	private String chargeNm;// 담당자
	private String officetel;// 담당자 연락처
}