package com.example.Revive.Controllers;

import com.example.Revive.Models.User;
import com.example.Revive.Request.AuthRequest;
import com.example.Revive.Services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/auth")
@RestController
public class AuthController {
    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User registerUser) {
        return authService.registerUserService(registerUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginCustomer(@RequestBody AuthRequest authRequest) {
        System.out.println("sidraaa: "+authRequest);
        return authService.loginUserService(authRequest);
    }
}
