package com.backend.rent_it.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.backend.rent_it.model.Peripheral;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PeripheralDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void savePeripheral(Peripheral peripheral) {
        String sql = "INSERT INTO peripherals (name, quantity, image, type, price, description) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, peripheral.getName(), peripheral.getQuantity(), peripheral.getImage(), peripheral.getType(), peripheral.getPrice(), peripheral.getDescription());
    }

    public List<Peripheral> getAllPeripherals() {
        String sql = "SELECT * FROM peripherals";
        return jdbcTemplate.query(sql, new RowMapper<Peripheral>() {
            @Override
            public Peripheral mapRow(ResultSet rs, int rowNum) throws SQLException {
                Peripheral peripheral = new Peripheral();
                peripheral.setId(rs.getInt("id"));
                peripheral.setName(rs.getString("name"));
                peripheral.setQuantity(rs.getInt("quantity"));
                peripheral.setImage(rs.getBytes("image"));  // Retrieving image as byte array
                peripheral.setType(rs.getString("type"));
                peripheral.setPrice(rs.getDouble("price"));
                peripheral.setDescription(rs.getString("description"));
                return peripheral;
            }
        });
    }

    public List<Peripheral> getAllPeripherals2() {
        String sql = "SELECT * FROM peripherals";
        return jdbcTemplate.query(sql, new PeripheralRowMapper());
    }

    public void deletePeripheral(int id) {
        String sql = "DELETE FROM peripherals WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void updatePeripheral(int id, int quantity, String type, double price, String description) {
        String sql = "UPDATE peripherals SET quantity = ?, type = ?, price = ?, description = ? WHERE id = ?";
        jdbcTemplate.update(sql, quantity, type, price, description, id);
    }    
}
