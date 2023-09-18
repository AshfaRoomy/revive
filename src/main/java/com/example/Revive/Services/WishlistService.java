package com.example.Revive.Services;

import com.example.Revive.Models.User;
import com.example.Revive.Response.MessageResponse;
import com.example.Revive.Models.Product;
import com.example.Revive.Models.Wishlist;
import com.example.Revive.Repositories.UserRepository;
import com.example.Revive.Repositories.ProductRepository;
import com.example.Revive.Repositories.WishlistRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@Service
public class WishlistService {
    private final WishlistRepository wishListRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public WishlistService(WishlistRepository wishListRepository, ProductRepository productRepository, UserRepository userRepository){
        this.wishListRepository = wishListRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> onWishListItem(Integer productId, HttpServletRequest request){
        Product product = productRepository.findById(productId).orElse(null);
        User user = userRepository.findByUsername(request.getUserPrincipal().getName()).orElse(null);
        System.out.println(wishListRepository.existsByProductAndUser(product, user));
        if (wishListRepository.existsByProductAndUser(product, user)){
            Wishlist deleteWishlist = wishListRepository.findByProductAndUser(product,user);
            wishListRepository.delete(deleteWishlist);
            return ResponseEntity.ok().body(new MessageResponse("Removed from your wishlist"));

        }else{
            Wishlist wishList = new Wishlist();
            wishList.setProduct(product);
            wishList.setUser(user);
            wishListRepository.save(wishList);
            return ResponseEntity.ok().body(wishList.getProduct());
        }
    }

    public List<Wishlist> getAllWishListItemsByToken(HttpServletRequest request){
        User user = userRepository.findByUsername(request.getUserPrincipal().getName()).orElse(null);
        List<Wishlist>wishList;
        wishList = wishListRepository.findByUserOrderByWishlistIdDesc(user);
        return wishList;
    }

    public ResponseEntity<?> getWishListProduct(Integer productId, HttpServletRequest request){
        User user = userRepository.findByUsername(request.getUserPrincipal().getName()).orElse(null);
        Product product = productRepository.findById(productId).orElse(null);
        if (wishListRepository.existsByProduct(product)&& wishListRepository.existsByUser(user)){
            Wishlist wishList = wishListRepository.findByProduct(product);
            System.out.println(wishList);
            return ResponseEntity.ok().body(wishList);

        }else{
            return ResponseEntity.ok().body("");

        }
    }
}

