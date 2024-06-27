package com.quinbaytraining.inventory.service;

import com.quinbaytraining.inventory.DTO.ProductDTO;
import com.quinbaytraining.inventory.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    Product getProductById(Long id);

    Product addProduct(Product product);

    Product updateProduct(long id, ProductDTO productDTO);

    String deleteProduct(Long id);
}
