package com.sds.animalapp.model.volunteer;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.animalapp.domain.VolunteerNotice;
import com.sds.animalapp.domain.VolunteerSelectParam;

@Service
public class VolunteerServiceImpl implements VolunteerService {
	
	@Autowired
	VolunteerDAO volunteerDAO;

	//게시물 수 카운트
	public int selectCount(String keyword) {
		return volunteerDAO.selectCount(keyword);
	}
	
	//봉사List모두 가져오기
	public List selectAll(VolunteerSelectParam volunteerSelectParam) {
		return volunteerDAO.selectAll(volunteerSelectParam);
	}
	
	@Override
	public VolunteerNotice select(int id) {
		return volunteerDAO.select(id);
	}

	//글 등록
	public void insert(VolunteerNotice volunteerNotice) {
		volunteerDAO.insert(volunteerNotice);
	}
	
	@Override
	public int selectRegistCount(int id) {
		return volunteerDAO.selectRegistCount(id);
	}

	@Override
	public void update(VolunteerNotice volunteerNotice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(VolunteerNotice volunteerNotice) {
		// TODO Auto-generated method stub
		
	}

	



}
