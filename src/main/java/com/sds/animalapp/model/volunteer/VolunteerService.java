package com.sds.animalapp.model.volunteer;

import java.util.List;

import com.sds.animalapp.domain.VolunteerNotice;
import com.sds.animalapp.domain.VolunteerSelectParam;

public interface VolunteerService {
	public int selectCount(String keyword); // 게시물 수

	public int selectRegistCount(int id);

	public List<VolunteerNotice> selectAll(VolunteerSelectParam volunteerSelectParam);// 모든 게시물 가져오기

	public VolunteerNotice select(int id);

	public void insert(VolunteerNotice volunteerNotice);

	public void update(VolunteerNotice volunteerNotice);

	public void delete(VolunteerNotice volunteerNotice);

	Integer findShelterIdxByCareNm(String careNm);
}