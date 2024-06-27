package com.quinbaytraining.inventory.service.implementation;

import com.quinbaytraining.inventory.DTO.ProductDTO;
import com.quinbaytraining.inventory.DTO.ProductHistoryDTO;
import com.quinbaytraining.inventory.model.Category;
import com.quinbaytraining.inventory.model.Product;
import com.quinbaytraining.inventory.repository.ProductRepository;
import com.quinbaytraining.inventory.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private SellerServiceImpl sellerService;

    @Autowired
    private ProductHistoryServiceImpl productHistoryService;


    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(long id, ProductDTO productDTO) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            if (!product.getSeller().getId().equals(productDTO.getSellerId())) {
                return null;
            }
            Category category = categoryService.getCategoryByName(productDTO.getCategoryName());
            if (category == null) {
                throw new IllegalArgumentException("Category with name " + productDTO.getCategoryName() + " not found");
            }
            ProductHistoryDTO productHistoryDTO = createProductHistoryDTO(product, productDTO);

            product.setProdName(productDTO.getProdName());
            product.setProdPrice(productDTO.getProdPrice());
            product.setProdQuantity(productDTO.getProdQuantity());
            product.setCategory(category);
            Product updatedProduct = productRepository.save(product);
            if (productHistoryDTO != null) {
                productHistoryService.createProductHistory(productHistoryDTO);
            }
            return updatedProduct;
        }
        return null;
    }


    @Override
    public String deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return "Product Successfully deleted";
        } else {
            return "Product with ID " + id + " not found.";
        }
    }


    private ProductHistoryDTO createProductHistoryDTO(Product product, ProductDTO productDTO) {
        ProductHistoryDTO productHistoryDTO = null;

        if (!product.getProdName().equals(productDTO.getProdName())) {
            productHistoryDTO = new ProductHistoryDTO();
            productHistoryDTO.setNewValue(productDTO.getProdName());
            productHistoryDTO.setOldValue(product.getProdName());
            productHistoryDTO.setModifiedColumn("prodName");
            productHistoryDTO.setProdId(product.getId());
        }

        if (product.getProdPrice() != productDTO.getProdPrice()) {
            if (productHistoryDTO == null) {
                productHistoryDTO = new ProductHistoryDTO();
            }
            productHistoryDTO.setNewValue(String.valueOf(productDTO.getProdPrice()));
            productHistoryDTO.setOldValue(String.valueOf(product.getProdPrice()));
            productHistoryDTO.setModifiedColumn("prodPrice");
            productHistoryDTO.setProdId(product.getId());
        }

        if (product.getProdQuantity() != productDTO.getProdQuantity()) {
            if (productHistoryDTO == null) {
                productHistoryDTO = new ProductHistoryDTO();
            }
            productHistoryDTO.setNewValue(String.valueOf(productDTO.getProdQuantity()));
            productHistoryDTO.setOldValue(String.valueOf(product.getProdQuantity()));
            productHistoryDTO.setModifiedColumn("prodQuantity");
            productHistoryDTO.setProdId(product.getId());
        }

        if (!product.getCategory().getCategoryName().equals(productDTO.getCategoryName())) {
            if (productHistoryDTO == null) {
                productHistoryDTO = new ProductHistoryDTO();
            }
            productHistoryDTO.setNewValue(productDTO.getCategoryName());
            productHistoryDTO.setOldValue(product.getCategory().getCategoryName());
            productHistoryDTO.setModifiedColumn("category");
            productHistoryDTO.setProdId(product.getId());
        }

        return productHistoryDTO;
    }
}
