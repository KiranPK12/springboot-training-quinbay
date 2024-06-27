package com.quinbaytraining.inventory.service;

import com.quinbaytraining.inventory.DTO.ProductHistoryDTO;
import com.quinbaytraining.inventory.model.ProductHistory;

import java.util.List;

public interface ProductHistoryService {
    ProductHistory createProductHistory(ProductHistoryDTO productHistoryDTO);

    ProductHistory getProductHistoryById(Long id);

    List<ProductHistory> getProductHistoriesByProductId(Long productId);

    List<ProductHistory> getAllProductHistories();

    boolean deleteProductHistory(Long id);
}
