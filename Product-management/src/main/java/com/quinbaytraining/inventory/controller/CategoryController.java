package com.quinbaytraining.inventory.controller;

import com.quinbaytraining.inventory.DTO.CategoryDTO;
import com.quinbaytraining.inventory.model.Category;
import com.quinbaytraining.inventory.service.implementation.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllCategories() {
        List<Category> categories = categoryService.getAll();
        if (categories.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No products found.");
        }
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Category> getCategoryByName(@PathVariable String name) {
        Category category = categoryService.getCategoryByName(name);
        if (category != null) {
            return new ResponseEntity<>(category, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getCategory/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        Category responseCategory = categoryService.getCategoryById(id);
        return responseCategory == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with ID " + id + " not found.") : ResponseEntity.ok(responseCategory);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody CategoryDTO categoryDTO) {
        Category category = Category.builder().categoryName(categoryDTO.getCategoryName()).build();
        Category addedCategory = categoryService.addCategory(category);
        return new ResponseEntity<>(addedCategory, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        Category updatedCategory = categoryService.updateCategory(id, categoryDTO);
        if (updatedCategory == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category with ID " + id + " not found.");
        }
        return ResponseEntity.ok("Category updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        String result = categoryService.deleteCategory(id);
        if (result.contains("not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
        return ResponseEntity.ok(result);
    }


}
