package com.sds.animalapp.model.volunteer;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sds.animalapp.domain.VolunteerApplication;

@Mapper
public interface VolunteerApplicationDAO {
    
    int insert(VolunteerApplication application);

    int delete(int id);

    VolunteerApplication findApplication(@Param("title") String title, @Param("volDate") String volDate, @Param("noticeId") int noticeId);

    List<VolunteerApplication> findByNoticeId(int noticeId);

    List<VolunteerApplication> findAll();
}
