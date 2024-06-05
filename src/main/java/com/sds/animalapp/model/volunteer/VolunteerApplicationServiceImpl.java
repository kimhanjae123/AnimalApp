package com.sds.animalapp.model.volunteer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.sds.animalapp.domain.VolunteerApplication;

@Service
public class VolunteerApplicationServiceImpl implements VolunteerApplicationService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void apply(VolunteerApplication application) {
        String sql = "INSERT INTO volunteer_application (title, vol_date, notice_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, application.getTitle(), application.getVolDate(), application.getNoticeId());
    }

    @Override
    public void cancel(int id) {
        String sql = "DELETE FROM volunteer_application WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<VolunteerApplication> getAllApplications() {
        String sql = "SELECT * FROM volunteer_application";
        return jdbcTemplate.query(sql, new VolunteerApplicationRowMapper());
    }

    @Override
    public List<VolunteerApplication> getApplicationsByNoticeId(int noticeId) {
        String sql = "SELECT * FROM volunteer_application WHERE notice_id = ?";
        return jdbcTemplate.query(sql, new VolunteerApplicationRowMapper(), noticeId);
    }

    private static class VolunteerApplicationRowMapper implements RowMapper<VolunteerApplication> {
        @Override
        public VolunteerApplication mapRow(ResultSet rs, int rowNum) throws SQLException {
            VolunteerApplication application = new VolunteerApplication();
            application.setId(rs.getInt("id"));
            application.setTitle(rs.getString("title"));
            application.setVolDate(rs.getString("vol_date"));
            application.setNoticeId(rs.getInt("notice_id"));
            return application;
        }
    }
}