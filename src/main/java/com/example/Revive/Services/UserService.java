package com.example.Revive.Services;

import com.example.Revive.Models.User;
import com.example.Revive.Repositories.UserRepository;
import com.example.Revive.Response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;

    }
    public ResponseEntity<?> getUserByUserId (Integer userId){
        if(userRepository.existsById(userId)){
            Optional<User> user = userRepository.findById(userId);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.badRequest().body(new MessageResponse("User not found!!!"));
    }

    public ResponseEntity<MessageResponse> updateUserPasswordByUserId(Integer userId, String newPassword){
        if(userRepository.existsById(userId)){
            User user = userRepository.findById(userId).get();
            user.setPassword(newPassword);
            userRepository.save(user);
            return ResponseEntity.ok(new MessageResponse("Successfully Updated"));
        }
        else{
            return ResponseEntity.badRequest().body(new MessageResponse("User Not Available!"));
        }
    }

    public User getUserByUserName(String email) {
        String username =userRepository.findByEmail(email).getUsername();
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username Not Found with username: " + username));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
