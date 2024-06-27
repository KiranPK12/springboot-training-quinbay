package com.quinbaytraining.inventory.service;

import com.quinbaytraining.inventory.DTO.CategoryDTO;
import com.quinbaytraining.inventory.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAll();

    Category getCategoryById(Long id);

    Category addCategory(Category category);

    Category updateCategory(long id, CategoryDTO categoryDTO);

    String deleteCategory(Long id);

    Category getCategoryByName(String categoryName);

}
