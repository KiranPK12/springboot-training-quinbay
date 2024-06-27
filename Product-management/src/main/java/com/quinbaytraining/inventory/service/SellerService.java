package com.quinbaytraining.inventory.service;

import com.quinbaytraining.inventory.DTO.SellerDTO;
import com.quinbaytraining.inventory.model.Seller;

import java.util.List;

public interface SellerService {
    Seller createSeller(SellerDTO sellerDTO);

    List<Seller> getAllSellers();

    Seller getSellerById(Long id);

    Seller updateSeller(Long id, SellerDTO sellerDTO);

    boolean deleteSeller(Long id);
}
