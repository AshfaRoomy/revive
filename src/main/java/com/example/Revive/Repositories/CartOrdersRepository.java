package com.example.Revive.Repositories;

import com.example.Revive.Models.CartOrder;
import com.example.Revive.Models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartOrdersRepository  extends JpaRepository<CartOrder, Integer> {
    List<CartOrder> findByOrders(Orders orders);

    List<CartOrder> findByOrdersUserUserIdOrderByCartOrderId(Integer userId);
}
