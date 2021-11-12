package com.example.apiweblaptop.repo;

import com.example.apiweblaptop.entity.DetailOrder;
import com.example.apiweblaptop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface DetailOrderRepository extends JpaRepository<DetailOrder, Long> {
    List<DetailOrder> findOrderDetailsByOrder(Order order);
}
