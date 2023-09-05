package com.example.Revive.Controllers;

import com.example.Revive.Models.Cart;
import com.example.Revive.Services.CartService;
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/cart")
@RestController
public class CartController {
    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority ('CUSTOMER')")
    @PostMapping(value = "/add-cart/{productId}")
    public ResponseEntity<?> addCart(@PathVariable Integer productId, @RequestParam Integer cartQuantity, @RequestParam double totalPrice, HttpServletRequest request) {
        return cartService.addNewCartItem(productId, cartQuantity, totalPrice, request);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority ('CUSTOMER')")
    @GetMapping(value = "/cartAll")
    public List<Cart> getAllItemByCustomerId(HttpServletRequest request) {
        return cartService.getAllCartItems(request);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CUSTOMER')")
    @PostMapping(value = "/delete-cart/{productId}")
    public List<Cart> getAllItemByCartId(@PathVariable Integer productId, HttpServletRequest request) {
        return cartService.deleteCartProductByProductId(productId, request);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CUSTOMER')")
    @RequestMapping(value = "/update-cart/{cartId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCartItemByCustomerId(@PathVariable Integer cartId, @RequestParam Integer quantity) {
        return cartService.updateCartItem(cartId, quantity);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CUSTOMER')")
    @GetMapping(value = "/cartAll/{customerId}")
    public List<Cart> getAllCustomerIsPurchaseCartProducts(@PathVariable Integer userId, @RequestParam boolean isPurchased, HttpServletRequest request) {
        return cartService.getAllPendingCartProducts(userId, isPurchased, request);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CUSTOMER')")
    @DeleteMapping(value = "/delete/{cartId}")
    public List<Cart> deleteCartId(@PathVariable Integer cartId, HttpServletRequest request){
        return  cartService.deleteCartIdByCartId(cartId, request);
    }


}