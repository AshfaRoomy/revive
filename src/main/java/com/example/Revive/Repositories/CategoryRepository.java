package com.example.Revive.Repositories;

import com.example.Revive.Models.Category;
import com.example.Revive.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsByCategoryName(String categoryName);
    List<Category> findByCategoryNameStartsWith (String categoryType);
}
