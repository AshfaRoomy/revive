package com.example.Revive.Repositories;

import com.example.Revive.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
    Role findByRole(String role);
}
