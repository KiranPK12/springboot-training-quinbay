package com.quinbaytraining.inventory.service.implementation;

import com.quinbaytraining.inventory.DTO.CategoryDTO;
import com.quinbaytraining.inventory.model.Category;
import com.quinbaytraining.inventory.repository.CategoryRepository;
import com.quinbaytraining.inventory.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(long id, CategoryDTO categoryDTO) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setCategoryName(categoryDTO.getCategoryName());
            return categoryRepository.save(category);
        }
        return null;
    }

    @Override
    public String deleteCategory(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return "Category Successfully deleted";
        } else {
            return "Product with ID " + id + " not found.";
        }
    }

    @Override
    public Category getCategoryByName(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName).orElse(null);
    }

}
