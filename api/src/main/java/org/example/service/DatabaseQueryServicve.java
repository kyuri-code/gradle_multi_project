package org.example.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class DatabaseQueryServicve {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseQueryServicve(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> getDataFromDatabase() {
        String sql = "SELECT * FROM sample_table";

        return jdbcTemplate.query(sql, new RowMapper<String>() {
            @Override
            public String mapRow(@SuppressWarnings("null") ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("name");
            }
        });
    }

}