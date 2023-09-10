package com.example.Revive.Repositories;

import com.example.Revive.Models.Cart;
import com.example.Revive.Models.CartOrder;
import com.example.Revive.Models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartOrdersRepository extends JpaRepository<CartOrder, Integer> {
    List<CartOrder> findByOrders(Orders orders);

    List<CartOrder> findByOrdersUserUserIdOrderByCartOrderId(Integer userId);
//@Query("SELECT co FROM CartOrder co " +
//        "JOIN co.orders o " +
//        "JOIN co.cart c " +
//        "JOIN o.user u " +
//        "WHERE u.userId = :userId")
//List<CartOrder> findCartOrdersByUserId(@Param("userId") Integer userId);

}