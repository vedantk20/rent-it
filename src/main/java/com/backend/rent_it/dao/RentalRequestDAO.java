package com.backend.rent_it.dao;

import com.backend.rent_it.model.RentalRequest;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class RentalRequestDAO {
    private final JdbcTemplate jdbcTemplate;

    public RentalRequestDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(RentalRequest rentalRequest) {
        String sql = "INSERT INTO rentalrequests (clientName, clientEmail, clientPhone, pcModel, quantity, totalPrice, status, paymentStatus, requestDate, fromDate, toDate) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, rentalRequest.getClientName(), rentalRequest.getClientEmail(), rentalRequest.getClientPhone(),
                            rentalRequest.getPcModel(), rentalRequest.getQuantity(), rentalRequest.getTotalPrice(),
                            rentalRequest.getStatus(), rentalRequest.getPaymentStatus(), rentalRequest.getRequestDate(),
                            rentalRequest.getFromDate(), rentalRequest.getToDate());
    }

    public List<RentalRequest> findAll() {
        String sql = "SELECT * FROM rentalrequests";
        return jdbcTemplate.query(sql, new RentalRequestRowMapper());
    }

    public List<RentalRequest> findByClientEmail(String clientEmail) {
        String sql = "SELECT * FROM rentalrequests WHERE clientEmail = ?";
        return jdbcTemplate.query(sql, new RentalRequestRowMapper(), clientEmail);
    }

    public void updateStatus(int requestId, String status) {
        String sql = "UPDATE rentalrequests SET status = ? WHERE requestId = ?";
        jdbcTemplate.update(sql, status, requestId);
    }

    public void updatePaymentStatus(int requestId, String paymentStatus) {
        // Step 1: Update the payment status in the rentalrequests table
        String updatePaymentStatusSql = "UPDATE rentalrequests SET paymentStatus = ? WHERE requestId = ?";
        jdbcTemplate.update(updatePaymentStatusSql, paymentStatus, requestId);
    
        // Step 2: Fetch the pcModel and quantity from rentalrequests where requestId matches
        String fetchRentalRequestSql = "SELECT pcModel, quantity FROM rentalrequests WHERE requestId = ?";
        Map<String, Object> rentalRequest = jdbcTemplate.queryForMap(fetchRentalRequestSql, requestId);
        String pcModel = (String) rentalRequest.get("pcModel");
        int rentalQuantity = (Integer) rentalRequest.get("quantity");
    
        // Step 3: Update the quantity in the peripherals table where name matches pcModel
        String updatePeripheralsSql = "UPDATE peripherals SET quantity = quantity - ? WHERE name = ?";
        jdbcTemplate.update(updatePeripheralsSql, rentalQuantity, pcModel);
    }
    

    public RentalRequest findById(int requestId) {
        String sql = "SELECT * FROM rentalrequests WHERE requestId = ?";
        return jdbcTemplate.queryForObject(sql, new RentalRequestRowMapper(), requestId);
    }

    private static class RentalRequestRowMapper implements RowMapper<RentalRequest> {
        @Override
        public RentalRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
            RentalRequest rentalRequest = new RentalRequest();
            rentalRequest.setRequestId(rs.getInt("requestId"));
            rentalRequest.setClientName(rs.getString("clientName"));
            rentalRequest.setClientEmail(rs.getString("clientEmail"));
            rentalRequest.setClientPhone(rs.getString("clientPhone"));
            rentalRequest.setPcModel(rs.getString("pcModel"));
            rentalRequest.setQuantity(rs.getInt("quantity"));
            rentalRequest.setTotalPrice(rs.getBigDecimal("totalPrice"));
            rentalRequest.setStatus(rs.getString("status"));
            rentalRequest.setPaymentStatus(rs.getString("paymentStatus"));
            rentalRequest.setRequestDate(rs.getTimestamp("requestDate"));
            rentalRequest.setFromDate(rs.getDate("fromDate"));
            rentalRequest.setToDate(rs.getDate("toDate"));
            return rentalRequest;
        }
    }
}
