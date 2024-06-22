package com.sds.animalapp.domain;

import lombok.Data;

@Data
public class VolunteerNotice {
	private Integer shelter_idx;
	private int vol_event_post_idx;
	private String vol_date;
	private int recruit_count;
	private String shelter_name;
	private String body_text;
	private String regdate;
	private String title;
	private int member_idx;
}