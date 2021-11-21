package com.example.apiweblaptop.service;

import com.example.apiweblaptop.dto.OrderDTO;
import com.example.apiweblaptop.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    public List<OrderDTO> retrieveOrders();

    public Optional<OrderDTO> getOrder(Long orderId) throws ResourceNotFoundException;

    public OrderDTO saveOrder(OrderDTO order) throws ResourceNotFoundException;

    public Boolean deleteOrder(Long orderId) throws ResourceNotFoundException;

    public OrderDTO updateOrder(Long orderId,OrderDTO order) throws ResourceNotFoundException;

    public List<OrderDTO> findOrderByUser(Long userId) throws ResourceNotFoundException;

    public OrderDTO updateStatusOrder(Long orderId, String status) throws ResourceNotFoundException;

    public OrderDTO cancelOrder(Long orderId) throws ResourceNotFoundException;

    public OrderDTO acceptOrder(Long orderId) throws ResourceNotFoundException;

    public OrderDTO receiveOrder(Long orderId) throws ResourceNotFoundException;
}
