package com.example.Revive.Repositories;

import com.example.Revive.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User, Integer> {
    Optional<User> findByUsername(String name);
    User findByEmail(String email);
//    Why name and username
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
