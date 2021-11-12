package com.example.apiweblaptop.service;

import com.example.apiweblaptop.dto.Dashboard;
import com.example.apiweblaptop.dto.DetailOrderDTO;
import com.example.apiweblaptop.dto.OrderDetailResponseDTO;
import com.example.apiweblaptop.entity.DetailOrder;
import com.example.apiweblaptop.exception.AddDataFail;
import com.example.apiweblaptop.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface DetailOrderService {
    public List<DetailOrderDTO> retrieveOrderDetails();

    public Optional<DetailOrderDTO> getOrderDetail(Long detailId) throws ResourceNotFoundException;

    public DetailOrderDTO saveOrderDetail(DetailOrderDTO detailDTO) throws ResourceNotFoundException, AddDataFail;

    public Boolean deleteOrderDetail(Long detailId) throws ResourceNotFoundException;

    public DetailOrderDTO updateOrderDetail(Long id, DetailOrderDTO detailDTO) throws ResourceNotFoundException;

    public List<OrderDetailResponseDTO> findDetailByOrder(Long orderId) throws ResourceNotFoundException;

    public DetailOrderDTO restoreQty(Long id) throws ResourceNotFoundException;

    public List<Object> getTopProduct();

    public Dashboard getDashboard() throws Exception;
}
