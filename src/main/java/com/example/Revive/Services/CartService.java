package com.example.Revive.Services;

import com.example.Revive.Response.MessageResponse;
import com.example.Revive.Models.Cart;
import com.example.Revive.Models.User;
import com.example.Revive.Models.Product;
import com.example.Revive.Repositories.CartRepository;
import com.example.Revive.Repositories.UserRepository;
import com.example.Revive.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private CartRepository cartRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;

    @Autowired
    public CartService(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public ResponseEntity<?> addNewCartItem(Integer productId, Integer cartQuantity, Double totalPrice, HttpServletRequest request) {
        User user = userRepository.findByUsername(request.getUserPrincipal().getName()).get();
        Product product = productRepository.findById(productId).get();
        if (cartRepository.existsByUserAndProductAndIsPurchased(user,product,false)) {

            Cart cart = cartRepository.findByUserAndProduct(user,product);
            cart.setCartQuantity(cart.getCartQuantity() + cartQuantity);
            cart.setTotalPrice(totalPrice);
            cartRepository.save(cart);
            return ResponseEntity.ok().body(new MessageResponse("Added to the existing product"));
        } else {
            Cart cart = new Cart();
            cart.setProduct(product);
            cart.setUser(user);
            cart.setCartQuantity(cartQuantity);
            cart.setTotalPrice(totalPrice);
            cartRepository.save(cart);
            return ResponseEntity.ok().body(new MessageResponse("Added to your cart"));
        }
    }

    public List<Cart> deleteCartProductByProductId(Integer productId, HttpServletRequest request) {
        User user = userRepository.findByUsername(request.getUserPrincipal().getName()).get();
        if (cartRepository.existsByProductProductIdAndUserUserId(productId, user.getUserId())) {
            Cart cart = cartRepository.findByProductProductIdAndUserUserId(productId, user.getUserId());
            cartRepository.delete(cart);
            return getAllCartItems(request);
        } else {
            return null;
        }
    }

    public List<Cart> deleteCartIdByCartId(Integer cartId, HttpServletRequest request) {
        if (cartRepository.existsById(cartId)) {
            Cart cart = (Cart) cartRepository.findById(cartId).get();
            cartRepository.delete(cart);
            return getAllCartItems(request);
        } else {
            return null;
        }
    }

    public ResponseEntity<?> updateCartItem(Integer cartId, int quantity) {
        if (cartRepository.existsById(cartId)) {
            Cart cart = cartRepository.findById(cartId).get();
            double price = cart.getProduct().getPrice() * quantity;
            cart.setCartQuantity(quantity);
            cart.setTotalPrice(price);
            cartRepository.save(cart);
            return ResponseEntity.ok().body("");
        }
        return ResponseEntity.ok().body("");
    }

    public List<Cart> getAllCartItems(HttpServletRequest request) {
        User user = userRepository.findByUsername(request.getUserPrincipal().getName()).get();
        List<Cart> cartList = cartRepository.findByUserAndIsPurchasedOrderByCartIdDesc(user , false);
        return cartList;
    }

    public List<Cart> getAllPendingCartProducts(Integer userId, boolean isPurchased, HttpServletRequest request) {
        User user = userRepository.findById(userId).get();
        List<Cart> pendingCartList = cartRepository.findByUserAndIsPurchased(user, isPurchased);
        return pendingCartList;
    }

}
