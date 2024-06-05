package com.sds.animalapp.model.volunteer;

import java.util.List;

import com.sds.animalapp.domain.VolunteerApplication;

public interface VolunteerApplicationService {
    void apply(VolunteerApplication application);
    void cancel(int id);
    List<VolunteerApplication> getAllApplications();
    List<VolunteerApplication> getApplicationsByNoticeId(int noticeId);
}
