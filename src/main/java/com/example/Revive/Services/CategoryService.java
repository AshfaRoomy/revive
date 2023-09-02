package com.example.Revive.Services;


import com.example.Revive.Repositories.CategoryRepository;
import com.example.Revive.Response.MessageResponse;
import com.example.Revive.Models.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    //add a new category
    public ResponseEntity<?> addCategory(Category newCategory) {
        if (categoryRepository.existsByCategoryName(newCategory.getCategoryName())) {
            return ResponseEntity.badRequest().body("Category is already there!");
        } else {
            Category category = new Category();
            category.setCategoryName(newCategory.getCategoryName());
            categoryRepository.save(category);
            return ResponseEntity.ok(new MessageResponse("Successfully Created a New Category"));
        }
    }

    //delete category by the given id
    public ResponseEntity<?> deleteCategory(Integer categoryId) {
        if (categoryRepository.existsById(categoryId)) {
            categoryRepository.deleteById(categoryId);
            return ResponseEntity.ok("Successfully Deleted the category");
        } else {
            return ResponseEntity.ok("There is no such category id");
        }
    }

    //get all category
    public List<Category> getAllCategory() {
        System.out.println(categoryRepository.findAll());
        return categoryRepository.findAll();
    }

    public ResponseEntity<?> getCategoryById(Integer categoryId){
        if(categoryRepository.existsById(categoryId)){
            return ResponseEntity.ok(categoryRepository.findById(categoryId).get());
        }
        return  ResponseEntity.ok("There is no such category");
    }

    public List<Category>onGetAllCategoriesByName(String categoryType){
        System.out.println("sidra2: "+categoryType);
        return categoryRepository.findByCategoryNameStartsWith(categoryType);
    }

}
