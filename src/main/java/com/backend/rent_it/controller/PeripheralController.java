package com.backend.rent_it.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.backend.rent_it.dao.PeripheralDAO;
import com.backend.rent_it.model.Peripheral;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api")
public class PeripheralController {

    @Autowired
    private PeripheralDAO peripheralDAO;

    @PostMapping("/peripherals")
    public ResponseEntity<String> addPeripheral(
            @RequestParam("name") String name,
            @RequestParam("quantity") int quantity,
            @RequestParam("image") MultipartFile image,
            @RequestParam("type") String type,
            @RequestParam("price") double price,
            @RequestParam("description") String description) {

        try {
            Peripheral peripheral = new Peripheral();
            peripheral.setName(name);
            peripheral.setQuantity(quantity);
            peripheral.setImage(image.getBytes()); // Convert MultipartFile to byte array
            peripheral.setType(type);
            peripheral.setPrice(price);
            peripheral.setDescription(description);

            peripheralDAO.savePeripheral(peripheral);
            return new ResponseEntity<>("Peripheral added successfully!", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error adding peripheral: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/peripherals")
    public ResponseEntity<List<Peripheral>> getAllPeripherals() {
        List<Peripheral> peripherals = peripheralDAO.getAllPeripherals();
        for (Peripheral p : peripherals) {
            // Convert byte[] to Base64 string
            p.setImage(p.getImage());
        }
        return ResponseEntity.ok(peripherals);
    }

    @DeleteMapping("/peripherals/{id}")
    public void deletePeripheral(@PathVariable int id) {
        peripheralDAO.deletePeripheral(id);
    }

    @PutMapping("/peripherals/{id}")
    public ResponseEntity<String> updatePeripheral(
            @PathVariable int id,
            @RequestParam("quantity") int quantity,
            @RequestParam("type") String type,
            @RequestParam("price") double price,
            @RequestParam("description") String description) {

        try {
            peripheralDAO.updatePeripheral(id, quantity, type, price, description);
            return new ResponseEntity<>("Peripheral updated successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating peripheral: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
