package com.example.apiweblaptop.repo;

import com.example.apiweblaptop.entity.Role;
import com.example.apiweblaptop.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
