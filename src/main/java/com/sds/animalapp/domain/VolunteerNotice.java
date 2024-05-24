package com.sds.animalapp.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class VolunteerNotice {
	
	private int vol_event_post_idx;
	private String vol_date;
	private String recruit_count;
	private String shelter_name;
	private String body_text;
	private String regdate;
	private String title;
	
	
}
