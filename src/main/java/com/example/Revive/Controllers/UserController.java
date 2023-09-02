package com.example.Revive.Controllers;

import com.example.Revive.Models.User;
import com.example.Revive.Response.MessageResponse;
//import com.example.Revive.Services.OTPService;
import com.example.Revive.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/users")
@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CUSTOMER')")
    @RequestMapping(value = "/user/{userId}")
    public ResponseEntity<?> getAUser(@PathVariable Integer userId) {

        return userService.getUserByUserId(userId);
    }
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CUSTOMER')")
    @PutMapping(value = "/reset-password/{userId}")
    public ResponseEntity<MessageResponse> updateUserPasswordByUserId(@PathVariable Integer userId, @RequestBody String newPassword) {
        return userService.updateUserPasswordByUserId(userId, newPassword);
    }

}
