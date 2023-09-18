package com.example.Revive.Services;


import com.example.Revive.Models.Product;
import com.example.Revive.Models.RateReview;
import com.example.Revive.Models.User;
import com.example.Revive.Repositories.ProductRepository;
import com.example.Revive.Repositories.RateReviewRepository;
import com.example.Revive.Repositories.UserRepository;
import com.example.Revive.Response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class RateReviewService {

    private UserRepository userRepository;
    private ProductRepository productRepository;
    private RateReviewRepository rateReviewRepository;

    @Autowired
    public RateReviewService(RateReviewRepository rateReviewRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.rateReviewRepository = rateReviewRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public ResponseEntity<MessageResponse> onAddRateReviewByProductId(Integer productId, RateReview newRateReview, HttpServletRequest request) {
        if (productRepository.existsById(productId)) {
            User user = userRepository.findByUsername(request.getUserPrincipal().getName()).get();
            Product product = productRepository.findById(productId).get();
            RateReview rateReview = new RateReview();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            System.out.println(dtf.format(now));
            rateReview.setDate(dtf.format(now));
            rateReview.setUser(user);
            rateReview.setProduct(product);
            rateReview.setFeedback(newRateReview.getFeedback());
            rateReview.setRate(newRateReview.getRate());
            rateReviewRepository.save(rateReview);
            System.out.println("Rate:"+ rateReview);

            return ResponseEntity.ok().body(new MessageResponse("Thank you for your feedback"));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Product Not Available"));
        }
    }
    public List<RateReview> getRateReviewByProductId(Integer productId, HttpServletRequest request){
        List<RateReview> rateReviewsList = rateReviewRepository.findByProductProductId(productId);
        return rateReviewsList;
    }

    public RateReview getRateReviewById(Integer rateReviewId){
        RateReview rateReview = rateReviewRepository.findById(rateReviewId).get();
        return rateReview;
    }
}