package com.example.Revive.Controllers;

import com.example.Revive.Models.Category;
import com.example.Revive.Services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = ("*"))
@RequestMapping("api/category")
@RestController
public class CategoryController {
    private CategoryService categoryService;
    public CategoryController( CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/addCategory")
    public ResponseEntity<?> addCategory(@RequestBody Category newCategory, HttpServletRequest httpServletRequest){
        System.out.println("ashfaa: "+newCategory);
        return categoryService.addCategory(newCategory);
    }
    @GetMapping(value = "/all")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategory();
    }


    //    delete product
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(value = "/deleteCategory/{categoryId}")
    public void deleteCategory(@PathVariable Integer categoryId) {
        categoryService.deleteCategory(categoryId);
    }

    @GetMapping(value="/category/{categoryId}")
    public ResponseEntity<?> getCategoryById (@PathVariable Integer categoryId){
        return categoryService.getCategoryById(categoryId);
    }
    @GetMapping(value = "category-product/{categoryType}")
    public List<Category>onGetAllCategoriesByName(@PathVariable String categoryType){
        System.out.println("sidra: "+categoryType);
        return categoryService.onGetAllCategoriesByName(categoryType);
    }


}
