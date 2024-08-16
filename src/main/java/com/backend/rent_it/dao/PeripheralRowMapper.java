package com.backend.rent_it.dao;

import org.springframework.jdbc.core.RowMapper;

import com.backend.rent_it.model.Peripheral;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PeripheralRowMapper implements RowMapper<Peripheral> {
    @Override
    public Peripheral mapRow(ResultSet rs, int rowNum) throws SQLException {
        Peripheral peripheral = new Peripheral();
        peripheral.setId(rs.getInt("id"));
        peripheral.setName(rs.getString("name"));
        peripheral.setQuantity(rs.getInt("quantity"));
        peripheral.setImage(rs.getBytes("image")); // Retrieve image as byte array
        peripheral.setType(rs.getString("type"));
        peripheral.setPrice(rs.getDouble("price"));
        peripheral.setDescription(rs.getString("description"));
        return peripheral;
    }
}
