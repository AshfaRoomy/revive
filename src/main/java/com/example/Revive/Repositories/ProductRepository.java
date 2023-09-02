package com.example.Revive.Repositories;

import com.example.Revive.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findByProductName(String productName);
    Product findByBrand(String brand);

    List<Product> findByCategory(String categoryType);

    boolean existsByProductName(String productName);

//    List<Product> findByCategoryStartsWith(String categoryType);
}
