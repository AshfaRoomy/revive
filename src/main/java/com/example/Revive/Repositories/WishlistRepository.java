package com.example.Revive.Repositories;

import com.example.Revive.Models.User;
import com.example.Revive.Models.Product;
import com.example.Revive.Models.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist,Integer> {

    List<Wishlist> findByUserOrderByWishlistIdDesc(User user);

    Wishlist findByProduct(Product product);

    boolean existsByUser (User user);

    boolean existsByProduct (Product product);
    Wishlist findByProductAndUser(Product product, User user);
    boolean existsByProductAndUser(Product product, User user);
}

