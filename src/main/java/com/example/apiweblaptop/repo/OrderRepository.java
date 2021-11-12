package com.example.apiweblaptop.repo;

import com.example.apiweblaptop.entity.Order;
import com.example.apiweblaptop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> getOrderByUser(User user);
}
