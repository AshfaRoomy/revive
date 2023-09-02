package com.example.Revive.Repositories;

import com.example.Revive.Models.Cart;
import com.example.Revive.Models.User;
import com.example.Revive.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    boolean existsByUserAndProductAndIsPurchased(User user, Product product, boolean isPurchased);
    Cart findByProductProductIdAndUserUserId(Integer productId, Integer userId);

//    boolean existsByUserAndProductAndIsPurchased(User user, Product productId, boolean isPurchased);

    boolean existsByProductProductIdAndUserUserId(Integer productId, Integer userId);

    Cart findByUserAndProduct(User user, Product productId);
    Cart findByUserAndProductAndCartQuantity(User user, Product productId, int quantity);

    List<Cart> findByUserAndIsPurchased(User userId, boolean isPurchased);

    List<Cart> findByUserAndIsPurchasedOrderByCartIdDesc(User user, boolean isPurchased);

}
