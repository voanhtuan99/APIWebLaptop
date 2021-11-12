package com.example.apiweblaptop.repo;

import com.example.apiweblaptop.dto.UserDTO;
import com.example.apiweblaptop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailAndActivestatusNot(String email, String activestatus);

    List<User> findAllByOrderByIdAsc();

    List<User> findByActivestatus(String status);

    List<User> findAllByRoleIdAndActivestatus(Long id, String active);
}
