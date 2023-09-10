package com.example.Revive.Controllers;


import com.example.Revive.Models.RateReview;
import com.example.Revive.Response.MessageResponse;
import com.example.Revive.Services.RateReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")

@RequestMapping("api/rate")
@RestController
public class RateReviewController {

    private RateReviewService rateReviewService;

    public RateReviewController(RateReviewService rateReviewService) {
        this.rateReviewService = rateReviewService;
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CUSTOMER')")
    @PostMapping("/product-rate/{productId}")
    public ResponseEntity<MessageResponse> onAddRateReviewByProductId(@PathVariable Integer productId, @RequestBody RateReview newRateReview, HttpServletRequest request) {
        System.out.println("Rate review:"+ productId);
        return rateReviewService.onAddRateReviewByProductId(productId, newRateReview, request);
    }

    //    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CUSTOMER')")
    @GetMapping("/product-all/{productId}")
    public List<RateReview> getRateReviewByProductId(@PathVariable Integer productId, HttpServletRequest request) {
        System.out.println("lets see: "+productId);
        return rateReviewService.getRateReviewByProductId(productId, request);
    }

    //    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CUSTOMER')")
    @GetMapping("/product/rate/{rateReviewId}")
    public RateReview getRateReviewById(@PathVariable Integer rateReviewId) {
        System.out.println("lets see: "+rateReviewId);
        return rateReviewService.getRateReviewById(rateReviewId);
    }

}