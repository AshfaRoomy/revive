package com.example.Revive.Services;

import com.example.Revive.Response.MessageResponse;
import com.example.Revive.Models.*;
import com.example.Revive.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class OrdersService {
    private OrderRepository ordersRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private CartRepository cartRepository;
    private CartOrdersRepository cartOrdersRepository;

    @Autowired
    public OrdersService(OrderRepository ordersRepository, UserRepository userRepository, ProductRepository productRepository, CartRepository cartRepository, CartOrdersRepository cartOrdersRepository){
        this.ordersRepository = ordersRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartOrdersRepository = cartOrdersRepository;
    }
//    public List<Orders> getAllUserOrders(Integer userId, String status, HttpServletRequest request) {
//        User user = userRepository.findById(userId).get();
//        List<Orders> ordersList = ordersRepository.findByUserAndStatusOrderByOrdersIdDesc(user, status);
//        return ordersList;
//    }

    public Orders getAOrderById(Integer orderId) {
        Orders orders = ordersRepository.findById(orderId).get();
        return orders;
    }

    //details about the order date and amount only
    public ResponseEntity<Orders> addOrder(Orders newOrder, HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        User user = userRepository.findByUsername(username).get();
        System.out.println("user is: "+user);
        Orders orders = new Orders();
        orders.setUser(user);
        orders.setAddress(newOrder.getAddress());
        orders.setPhone(newOrder.getPhone());
        orders.setTotal(newOrder.getTotal());
        orders.setDate(newOrder.getDate());
        ordersRepository.save(orders);

        return ResponseEntity.ok().body(orders);
    }

    public List<CartOrder> getAllCartOrders() {
        List<CartOrder> cartOrderList = cartOrdersRepository.findAll();
        System.out.println("cart order list ="+ cartOrderList);
        return cartOrderList;
    }

    public List<CartOrder> getAllCartByOrderId(Integer ordersId) {
        Orders orders = ordersRepository.findById(ordersId).get();
        List<CartOrder> cartList = cartOrdersRepository.findByOrders(orders);
        System.out.println("Orders:"+orders);
        return cartList;
    }

//    public List<CartOrder> getAllCartOrdersByUserId(Integer customerId, HttpServletRequest request) {
//        List<CartOrder> cartOrdersList = cartOrdersRepository.findByOrdersCustomerCustomerIdOrderByCartOrderId(customerId);
//        return cartOrdersList;
//    }


//details about the order id user id the products that are made under this order
    public ResponseEntity<MessageResponse> addCartOrders(CartOrder newCartOrders, HttpServletRequest request) {
        System.out.println("sid newCartOrders: "+newCartOrders);

        CartOrder cartOrders = new CartOrder();
        cartOrders.setOrders(newCartOrders.getOrders());
        cartOrders.setCart(newCartOrders.getCart());
        Product product = newCartOrders.getCart().getProduct();

        product.setQuantity(newCartOrders.getCart().getProduct().getQuantity() - newCartOrders.getCart().getCartQuantity());
        System.out.println("sid product: "+product);
        productRepository.save(product);
        Cart cart = newCartOrders.getCart();
        cart.setPurchased(true);
        cartRepository.save(cart);
        System.out.println("cart orders before save: "+cartOrders);
        cartOrdersRepository.save(cartOrders);
        return ResponseEntity.ok().body(new MessageResponse("Your orders have been made successfully"));
    }
    public List<CartOrder> getAllCartOrdersByUserId(Integer userId, HttpServletRequest request) {
        List<CartOrder> cartOrderList = cartOrdersRepository.findByOrdersUserUserIdOrderByCartOrderId(userId);
        return cartOrderList;
    }

//    public ResponseEntity<CartOrder> updateOrderStatus(Integer cartOrdersId, CartOrder updateCartOrders) {
//        if (cartOrdersRepository.existsById(cartOrdersId)) {
//            CartOrder cartOrders = cartOrdersRepository.findById(cartOrdersId).get();
//            Product product = cartOrders.getCart().getProduct();
//            cartOrders.getOrders().setDate(updateCartOrders.getOrders().getDate());
//            cartOrders.getOrders().setStatus(updateCartOrders.getOrders().getStatus());
//            product.setQuantity(product.getQuantity() + updateCartOrders.getCart().getQuantity());
//            productRepository.save(product);
//            cartOrdersRepository.save(cartOrders);
//            return ResponseEntity.ok().body(cartOrders);
//        }
//        return null;
//    }

//    public ResponseEntity<?> updateOrderStatusByOrderId(Integer ordersId, String status) {
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//        String date = sdf.format(new Date());
//
//        if (ordersRepository.existsById(ordersId)) {
//            Orders orders = ordersRepository.findById(ordersId).get();
//            orders.setStatus(status);
//            orders.setDate(date);
//            List<CartOrder> cartOrdersList = cartOrdersRepository.findByOrders(orders);
//            for (CartOrder cartOrders : cartOrdersList) {
//                Product product = cartOrders.getCart().getProduct();
//                product.setQuantity(product.getQuantity() + cartOrders.getCart().getQuantity());
//                productRepository.save(product);
//                ordersRepository.save(orders);
//            }
//            return ResponseEntity.ok().body(orders);
//        }
//        return ResponseEntity.ok().body(new MessageResponse("No order id available"));
//    }
}
