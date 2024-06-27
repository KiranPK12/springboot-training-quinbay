package com.quinbaytraining.inventory.controller;

import com.quinbaytraining.inventory.DTO.ProductHistoryDTO;
import com.quinbaytraining.inventory.model.ProductHistory;
import com.quinbaytraining.inventory.service.implementation.ProductHistoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productHistory")
public class ProductHistoryController {

    @Autowired
    private ProductHistoryServiceImpl productHistoryService;

    @PostMapping("/add")
    public ResponseEntity<ProductHistory> addProductHistory(@RequestBody ProductHistoryDTO productHistoryDTO) {
        ProductHistory productHistory = productHistoryService.createProductHistory(productHistoryDTO);
        return new ResponseEntity<>(productHistory, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductHistoryById(@PathVariable Long id) {
        ProductHistory productHistory = productHistoryService.getProductHistoryById(id);
        if (productHistory == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productHistory);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductHistory>> getAllProductHistories() {
        List<ProductHistory> productHistories = productHistoryService.getAllProductHistories();
        return ResponseEntity.ok(productHistories);
    }

    @GetMapping("/byProductId/{productId}")
    public ResponseEntity<List<ProductHistory>> getProductHistoriesByProductId(@PathVariable Long productId) {
        List<ProductHistory> productHistories = productHistoryService.getProductHistoriesByProductId(productId);
        return ResponseEntity.ok(productHistories);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProductHistory(@PathVariable Long id) {
        boolean deleted = productHistoryService.deleteProductHistory(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
