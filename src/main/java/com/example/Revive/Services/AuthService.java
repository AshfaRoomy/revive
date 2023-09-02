package com.example.Revive.Services;


import com.example.Revive.Models.Role;
import com.example.Revive.Models.User;
import com.example.Revive.Repositories.UserRepository;
import com.example.Revive.Request.AuthRequest;
import com.example.Revive.Response.JwtResponse;
import com.example.Revive.Response.MessageResponse;
import com.example.Revive.Security.Service.UserDetailsImpl;
import com.example.Revive.Security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private RoleService roleService;
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private JwtUtils jwtUtils;



    @Autowired
    public AuthService(RoleService roleService, UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.roleService = roleService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;

    }

        public ResponseEntity<?> registerUserService(User registerUser) {
            if (userRepository.existsByUsername(registerUser.getUsername())) {
                return ResponseEntity.badRequest().body(new MessageResponse("Username already taken!"));
            }
            if (userRepository.existsByEmail(registerUser.getEmail())) {
                return ResponseEntity.badRequest().body(new MessageResponse(("Email already taken!")));
            }

            User user = new User();
            user.setUsername(registerUser.getUsername());
            user.setEmail(registerUser.getEmail());
            user.setPassword(passwordEncoder.encode(registerUser.getPassword()));
            Role role = roleService.getRoleByName("CUSTOMER");
            user.setRole(role);
            AuthRequest authRequest = new AuthRequest(registerUser.getEmail(), registerUser.getPassword());

            userRepository.save(user);

            // Log in the user after successful registration
            return loginUserService(authRequest);
        }


    public ResponseEntity<?> loginUserService(AuthRequest authRequest) {
        System.out.println("with profile");
        System.out.println(authRequest.getEmail());
        if (!userRepository.existsByEmail(authRequest.getEmail())) {
            System.out.println("Username " + authRequest.getEmail() + " doesn't exist");
            return ResponseEntity.badRequest().body(new MessageResponse("Username doesn't exist"));
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        Date expireTime = jwtUtils.expirationTime();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        System.out.println(new JwtResponse(jwt,
                userDetails.getUserId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles.get(0), expireTime));
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getUserId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles.get(0), expireTime));
    }
}


