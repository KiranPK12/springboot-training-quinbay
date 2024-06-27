package com.quinbaytraining.inventory.controller;

import com.quinbaytraining.inventory.DTO.ProductDTO;
import com.quinbaytraining.inventory.model.Category;
import com.quinbaytraining.inventory.model.Product;
import com.quinbaytraining.inventory.model.Seller;
import com.quinbaytraining.inventory.model.ValidationErrorResponse;
import com.quinbaytraining.inventory.service.implementation.CategoryServiceImpl;
import com.quinbaytraining.inventory.service.implementation.ProductServiceImpl;
import com.quinbaytraining.inventory.service.implementation.SellerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private SellerServiceImpl sellerService;


    @GetMapping("/getAll")
    public ResponseEntity<?> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No products found.");
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/getProduct/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        Product responseProduct = productService.getProductById(id);
        return responseProduct == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with ID " + id + " not found.") : ResponseEntity.ok(responseProduct);
    }


    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody ProductDTO productDTO) {
        if (productDTO.getProdPrice() <= 0) {
            ValidationErrorResponse errorResponse = new ValidationErrorResponse("Product price must be greater than 0", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        if (productDTO.getProdQuantity() < 0) {
            ValidationErrorResponse errorResponse = new ValidationErrorResponse("Product quantity must not be negative", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        Category category = categoryService.getCategoryByName(productDTO.getCategoryName());
        Seller seller = sellerService.getSellerById(productDTO.getSellerId());
        Product product = Product.builder().prodName(productDTO.getProdName()).prodPrice(productDTO.getProdPrice()).prodQuantity(productDTO.getProdQuantity()).category(category).seller(seller).build();
        Product addedProduct = productService.addProduct(product);
        return new ResponseEntity<>(addedProduct, HttpStatus.CREATED);
    }

    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        if (productDTO.getProdPrice() <= 0) {
            ValidationErrorResponse errorResponse = new ValidationErrorResponse("Product price must be greater than 0", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        if (productDTO.getProdQuantity() < 0) {
            ValidationErrorResponse errorResponse = new ValidationErrorResponse("Product quantity must not be negative", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        Product updatedProduct = productService.updateProduct(id, productDTO);
        if (updatedProduct == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with ID " + id + " not found.");
        }
        return ResponseEntity.ok("Product updated successfully");
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        String result = productService.deleteProduct(id);
        if (result.contains("not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
        return ResponseEntity.ok(result);
    }
}
