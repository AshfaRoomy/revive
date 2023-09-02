package com.example.Revive.Controllers;

import com.example.Revive.Models.Wishlist;
import com.example.Revive.Services.WishlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders ="*")
@RequestMapping("api/wishlist")
@RestController
public class WishlistController {
    private WishlistService wishListService;

    public WishlistController(WishlistService wishListService) {
        this.wishListService = wishListService;
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CUSTOMER')")
    @PostMapping(value = "/add-wishlist/{productId}")
    public ResponseEntity<?> addWishList(@PathVariable Integer productId, HttpServletRequest request){
        System.out.println("ashfa bam bam "+productId);
        return wishListService.onWishListItem(productId,request);
    }

//    Displaying all favourite products on a wishlist page

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority ('CUSTOMER')")
    @GetMapping(value = "/wishlist-All")
    public List<Wishlist> getAllWishListItemByUser(HttpServletRequest httpServletRequest){
        return wishListService.getAllWishListItemsByToken(httpServletRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CUSTOMER')")
    @GetMapping(value = "/product/{productId}")
    public ResponseEntity<?> getWishListProduct(@PathVariable Integer productId, HttpServletRequest request){
        return wishListService.getWishListProduct(productId,request);
    }



}

