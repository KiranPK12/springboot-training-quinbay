package com.quinbaytraining.inventory.service.implementation;

import com.quinbaytraining.inventory.DTO.ProductHistoryDTO;
import com.quinbaytraining.inventory.model.Product;
import com.quinbaytraining.inventory.model.ProductHistory;
import com.quinbaytraining.inventory.repository.ProductHistoryRepository;
import com.quinbaytraining.inventory.repository.ProductRepository;
import com.quinbaytraining.inventory.service.ProductHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductHistoryServiceImpl implements ProductHistoryService {

    private final ProductHistoryRepository productHistoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ProductHistoryServiceImpl(ProductHistoryRepository productHistoryRepository, ProductRepository productRepository) {
        this.productHistoryRepository = productHistoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public ProductHistory createProductHistory(ProductHistoryDTO productHistoryDTO) {
        Optional<Product> productOptional = productRepository.findById(productHistoryDTO.getProdId());
        if (productOptional.isEmpty()) {
            throw new IllegalArgumentException("Product with ID " + productHistoryDTO.getProdId() + " not found");
        }
        Product product = productOptional.get();
        ProductHistory productHistory = new ProductHistory();
        productHistory.setNewValue(productHistoryDTO.getNewValue());
        productHistory.setOldValue(productHistoryDTO.getOldValue());
        productHistory.setModifiedColumn(productHistoryDTO.getModifiedColumn());
        productHistory.setDateModified(String.valueOf(LocalDateTime.now()));
        productHistory.setProduct(product);
        return productHistoryRepository.save(productHistory);
    }

    @Override
    public ProductHistory getProductHistoryById(Long id) {
        Optional<ProductHistory> optionalProductHistory = productHistoryRepository.findById(id);
        return optionalProductHistory.orElse(null);
    }

    @Override
    public List<ProductHistory> getProductHistoriesByProductId(Long productId) {
        return productHistoryRepository.findByProductId(productId);
    }

    @Override
    public List<ProductHistory> getAllProductHistories() {
        return productHistoryRepository.findAll();
    }

    @Override
    public boolean deleteProductHistory(Long id) {
        if (productHistoryRepository.existsById(id)) {
            productHistoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
