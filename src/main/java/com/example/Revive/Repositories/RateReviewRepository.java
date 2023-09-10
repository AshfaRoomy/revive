package com.example.Revive.Repositories;


import com.example.Revive.Models.RateReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateReviewRepository extends JpaRepository<RateReview, Integer> {
    List<RateReview> findByProductProductId(Integer productId);
}