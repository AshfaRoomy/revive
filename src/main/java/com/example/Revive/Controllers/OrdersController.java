package com.example.Revive.Controllers;


import com.example.Revive.Models.CartOrder;
import com.example.Revive.Models.Product;
import com.example.Revive.Response.MessageResponse;
//import com.example.Revive.Models.CartOrder;
import com.example.Revive.Models.Orders;
import com.example.Revive.Services.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/orders")
@RestController

public class OrdersController {

    private OrdersService ordersService;

    @Autowired
    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CUSTOMER')")
    @PostMapping(value = "order/add-order")
        public ResponseEntity<Orders> addOrder(@RequestBody Orders newOrder, HttpServletRequest request) {
        return ordersService.addOrder(newOrder, request);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CUSTOMER')")
    @PostMapping(value = "/cart/add-order")
    public ResponseEntity<MessageResponse> addCartOrders(@RequestBody CartOrder newOrders, HttpServletRequest request) {
        System.out.println("the other one: "+newOrders);
        return ordersService.addCartOrders(newOrders, request);
    }


    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/cart-orders/{userId}")
    public List<CartOrder> getAllCartOrdersByUserId(@PathVariable Integer userId, HttpServletRequest request) {
        System.out.println("being called: "+userId);
        return ordersService.getAllCartOrdersByUserId(userId,request);
    }

    @RequestMapping(value="/all-cartOrders")
    public List<CartOrder> getAllCartOrders()
    {
        return ordersService.getAllCartOrders();
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CUSTOMER')")
    @GetMapping("/order/{orderId}")
    public Orders getAOrderById(@PathVariable Integer orderId) {
        return ordersService.getAOrderById(orderId);
    }


    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CUSTOMER')")
    @GetMapping(value = "/cart/{ordersId}")
    public List<CartOrder> getAllCartByOrderId(@PathVariable Integer ordersId, HttpServletRequest request) {
        return ordersService.getAllCartByOrderId(ordersId);
    }

}