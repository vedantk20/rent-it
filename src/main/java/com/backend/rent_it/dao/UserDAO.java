package com.backend.rent_it.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.backend.rent_it.model.User;

@Repository
public class UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveUser(User user) {
        String sql = "INSERT INTO users (email, password, name, phone, add1, add2, state, district, pincode) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getEmail(), user.getPassword(), user.getName(), user.getPhone(), user.getAdd1(), user.getAdd2(), user.getState(), user.getDistrict(), user.getPincode());
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setPhone(rs.getString("phone"));
                user.setAdd1(rs.getString("add1"));
                user.setAdd2(rs.getString("add2"));
                user.setState(rs.getString("state"));
                user.setDistrict(rs.getString("district"));
                user.setPincode(rs.getString("pincode"));
                return user;
            }
        });
    }

    @SuppressWarnings("deprecation")
    public User findUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        
        // Query the database for a user with the given email
        return jdbcTemplate.queryForObject(sql, new Object[]{email}, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setPhone(rs.getString("phone"));
                user.setAdd1(rs.getString("add1"));
                user.setAdd2(rs.getString("add2"));
                user.setState(rs.getString("state"));
                user.setDistrict(rs.getString("district"));
                user.setPincode(rs.getString("pincode"));
                return user;
            }
        });
    }
}
