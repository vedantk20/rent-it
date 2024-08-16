package com.backend.rent_it.controller;

import com.backend.rent_it.model.RentalRequest;
import com.backend.rent_it.dao.RentalRequestDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RentalRequestController {

    @Autowired
    private RentalRequestDAO rentalRequestDAO;

    @PostMapping("/rental-requests")
    public ResponseEntity<String> createRentalRequest(@RequestBody RentalRequest rentalRequest) {
        try {
            rentalRequest.setRequestDate(new Timestamp(new Date().getTime())); // Set current timestamp
            rentalRequestDAO.save(rentalRequest);
            return new ResponseEntity<>("Rental request submitted successfully!", HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>("Error submitting rental request", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/rental-requests/{email}")
    public List<RentalRequest> getRentalRequestsByEmail(@PathVariable("email") String email) {
        return rentalRequestDAO.findByClientEmail(email);
    }

    @GetMapping("/rentalRequests")
    public List<RentalRequest> getAllRentalRequests() {
        return rentalRequestDAO.findAll();
    }

    @PutMapping("/rentalRequests/{requestId}")
    public ResponseEntity<String> updateRentalRequestStatus(@PathVariable int requestId,
            @RequestBody Map<String, String> statusUpdate) {
        String status = statusUpdate.get("status");
        if (status == null) {
            return ResponseEntity.badRequest().body("Status is required");
        }

        // Validate status if needed
        if (!status.equals("Approved") && !status.equals("Rejected")) {
            return ResponseEntity.badRequest().body("Invalid status");
        }

        rentalRequestDAO.updateStatus(requestId, status);
        return ResponseEntity.ok("Status updated");
    }

    @PutMapping("/rentalRequests/pay/{requestId}")
    public ResponseEntity<String> updatePaymentStatus(@PathVariable int requestId,
            @RequestBody Map<String, String> paymentUpdate) {
        try {
            String paymentStatus = paymentUpdate.get("paymentStatus");
            System.out.println(paymentStatus);
            if (paymentStatus == null) {
                return ResponseEntity.badRequest().body("Payment status is required");
            }

            if (!paymentStatus.equals("Paid")) {
                return ResponseEntity.badRequest().body("Invalid payment status");
            }

            rentalRequestDAO.updatePaymentStatus(requestId, paymentStatus);

            return ResponseEntity.ok("Payment status updated");

        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>("Error submitting rental request", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
