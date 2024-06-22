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
    public void cancel(int notice_id, int member_idx) {
        volunteerApplicationDAO.delete(notice_id, member_idx);
    }

    @Override
    public List<VolunteerApplication> getAllApplications() {
        return volunteerApplicationDAO.findAll();
    }

    @Override
    public List<VolunteerApplication> getApplicationsByMemberIdx(int member_idx) {
        return volunteerApplicationDAO.findByMemberIdx(member_idx);
    }

    @Override
    public VolunteerApplication findById(int id) {
        return volunteerApplicationDAO.findApplicationById(id);
    }

    @Override
    public int getRecordNum(int notice_id, int member_idx) {
        return volunteerApplicationDAO.selectCount(notice_id, member_idx);
    }

	@Override
	public List getAllApplicant(int notice_id) {
		return volunteerApplicationDAO.selectNic(notice_id);
	}
}