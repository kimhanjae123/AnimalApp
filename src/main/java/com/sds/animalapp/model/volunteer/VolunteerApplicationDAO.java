package com.sds.animalapp.model.volunteer;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sds.animalapp.domain.VolunteerApplication;

@Mapper
public interface VolunteerApplicationDAO {
    int insert(VolunteerApplication application);
    int delete(@Param("notice_id") int notice_id, @Param("member_idx") int member_idx);
    VolunteerApplication findApplication(@Param("title") String title, @Param("vol_date") String vol_date, @Param("notice_id") int notice_id);
    List<VolunteerApplication> findByNoticeId(int notice_id);
    List<VolunteerApplication> findByMemberIdx(int member_idx);
    List<VolunteerApplication> findAll();
    VolunteerApplication findApplicationById(int member_idx); // 추가된 메서드
    int selectCount(@Param("notice_id") int notice_id, @Param("member_idx") int member_idx);
    List selectNic(int notice_id);
}
