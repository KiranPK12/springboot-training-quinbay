package com.quinbaytraining.inventory.controller;

import com.quinbaytraining.inventory.DTO.SellerDTO;
import com.quinbaytraining.inventory.model.Seller;
import com.quinbaytraining.inventory.service.implementation.SellerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    private SellerServiceImpl sellerService;

    @PostMapping("/add")
    public ResponseEntity<Seller> createSeller(@RequestBody SellerDTO sellerDTO) {
        Seller createdSeller = sellerService.createSeller(sellerDTO);
        return new ResponseEntity<>(createdSeller, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Seller>> getAllSellers() {
        List<Seller> sellers = sellerService.getAllSellers();
        return new ResponseEntity<>(sellers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSellerById(@PathVariable Long id) {
        Seller seller = sellerService.getSellerById(id);
        if (seller == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateSeller(@PathVariable Long id, @RequestBody SellerDTO sellerDTO) {
        Seller updatedSeller = sellerService.updateSeller(id, sellerDTO);
        if (updatedSeller == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Seller with ID " + id + " not found.");
        }
        return ResponseEntity.ok("Seller Details updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSeller(@PathVariable Long id) {
        boolean isDeleted = sellerService.deleteSeller(id);
        if (!isDeleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Seller with ID " + id + " not found.");
        }
        return ResponseEntity.ok("Seller Deleted successfully");
    }
}
