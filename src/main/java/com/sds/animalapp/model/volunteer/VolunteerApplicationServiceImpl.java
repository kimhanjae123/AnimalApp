package com.sds.animalapp.model.volunteer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.animalapp.domain.VolunteerApplication;

@Service
public class VolunteerApplicationServiceImpl implements VolunteerApplicationService {

    @Autowired
    private VolunteerApplicationDAO volunteerApplicationDAO;

    @Override
    public void apply(VolunteerApplication application) {
        volunteerApplicationDAO.insert(application);
    }

    @Override
<<<<<<< HEAD
    public void cancel(int noticeId, int member_idx) {
        volunteerApplicationDAO.delete(noticeId, member_idx);
=======
    public void cancel(int id, int member_idx) {
        volunteerApplicationDAO.delete(id, member_idx);
>>>>>>> aafef1acfb92816cd30bb6d72f90720bf08eba05
    }

    @Override
    public List<VolunteerApplication> getAllApplications() {
        return volunteerApplicationDAO.findAll();
    }

    @Override
    public List<VolunteerApplication> getApplicationsByMemberIdx(int memberIdx) {
        return volunteerApplicationDAO.findByMemberIdx(memberIdx);
    }

    @Override
    public VolunteerApplication findById(int id) {
        return volunteerApplicationDAO.findApplicationById(id);
    }
<<<<<<< HEAD
=======

	@Override
	public int getRecordNum(int noticeId, int member_idx) {
		return volunteerApplicationDAO.selectCount(noticeId, member_idx);
	}
}
>>>>>>> aafef1acfb92816cd30bb6d72f90720bf08eba05

    @Override
    public int getRecordNum(int noticeId, int member_idx) {
        return volunteerApplicationDAO.selectCount(noticeId, member_idx);
    }
}