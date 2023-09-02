package com.example.Revive.Repositories;

import com.example.Revive.Models.User;
import com.example.Revive.Models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Integer> {
//    List<Orders> findByUserAndStatusOrderByOrdersIdDesc(User user, String status);
//
//    List<Orders> findByStatus(String status);
}