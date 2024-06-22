package com.sds.animalapp.model.volunteer;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sds.animalapp.domain.VolunteerNotice;
import com.sds.animalapp.domain.VolunteerSelectParam;

@Mapper
public interface VolunteerDAO {
	public int selectCount(String keyword); // 게시물 수

	public int selectRegistCount(int id);

	public List<VolunteerNotice> selectAll(VolunteerSelectParam volunteerSelectParam);// 모든 게시물 가져오기

	public VolunteerNotice select(int id);

	public void insert(VolunteerNotice volunteer);

	public void update(VolunteerNotice volunteer);

	public void delete(VolunteerNotice volunteer);

	public Integer findShelterIdxByCareNm(String careNm);
}