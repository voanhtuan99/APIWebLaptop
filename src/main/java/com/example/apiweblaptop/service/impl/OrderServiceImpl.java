package com.example.apiweblaptop.service.impl;

import com.example.apiweblaptop.dto.*;
import com.example.apiweblaptop.entity.Order;
import com.example.apiweblaptop.entity.Product;
import com.example.apiweblaptop.entity.User;
import com.example.apiweblaptop.exception.ResourceNotFoundException;
import com.example.apiweblaptop.repo.DetailOrderRepository;
import com.example.apiweblaptop.repo.OrderRepository;
import com.example.apiweblaptop.repo.ProductRepository;
import com.example.apiweblaptop.repo.UserRepository;
import com.example.apiweblaptop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private ProductRepository productrepository;

    @Autowired
    private DetailOrderRepository detailOrderRepository;
    @Override
    public List<OrderDTO> retrieveOrders() {
        List<Order> orders = orderRepository.findAll();
        List<Order> ordersNew = orders.stream().sorted((o1, o2) -> {
            if(Integer.parseInt(String.valueOf(o1.getId())) < Integer.parseInt(String.valueOf(o2.getId())))
                return 1;
            else return -1;
        }).collect(Collectors.toList());
        return new OrderDTO().entityToDTO(ordersNew);
    }

    @Override
    public Optional<OrderDTO> getOrder(Long orderId) throws ResourceNotFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("order not found for this id: "+orderId));

        return Optional.of(new OrderDTO().entityToDTO(order));
    }

    @Override
    public OrderDTO saveOrder(OrderDTO orderDTO) throws ResourceNotFoundException {
        User user =userRepository.findById(orderDTO.getId_user()).orElseThrow(() ->
                new ResourceNotFoundException("user not found for this id: "+orderDTO.getId_user()));
        Order order = new OrderDTO().dtoToEntity(orderDTO);
        order.setUser(user);
        order.setStatus("Chờ xác nhận");
        order.setNgaydat(java.time.LocalDate.now());
        return new OrderDTO().entityToDTO(orderRepository.save(order));
    }

    @Override
    public Boolean deleteOrder(Long orderId) throws ResourceNotFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("order not found for this id: " + orderId));
        this.orderRepository.delete(order);
        return true;
    }

    @Override
    public OrderDTO updateOrder(Long orderId, OrderDTO order) throws ResourceNotFoundException {
        Order orderExist = orderRepository.findById(orderId).orElseThrow(() ->
                new ResourceNotFoundException("order not found for this id: "+orderId));


        orderExist.setStatus(order.getStatus());
        orderExist.setTotal_price(order.getTotal_price());

        Order orderr = new Order();
        orderr = orderRepository.save(orderExist);
        return new OrderDTO().entityToDTO(orderr);
    }
    @Override
    public OrderDTO receiveOrder(Long orderId) throws ResourceNotFoundException {
        Order orderExist = orderRepository.findById(orderId).orElseThrow(() ->
                new ResourceNotFoundException("order not found for this id: "+orderId));
        System.out.println(orderExist.getId());
        orderExist.setStatus("Đã nhận");
        return new OrderDTO().entityToDTO(orderExist);
    }
    @Override
    public List<OrderDTO> findOrderByUser(Long userId) throws ResourceNotFoundException {
        Optional<User> userExist = userRepository.findById(userId);
        if(!userExist.isPresent()){
            throw new ResourceNotFoundException(""+ ErrorCode.FIND_USER_ERROR);
        }
        User user = userExist.get();

        List<Order> list = null;
        list = orderRepository.getOrderByUser(user);

        List<OrderDTO> orderDTOS = new ArrayList<>();
        orderDTOS = new OrderDTO().entityToDTO(list);
        return orderDTOS;
    }
    // cancel order
    @Override
    public OrderDTO cancelOrder(Long orderId) throws ResourceNotFoundException {
        Order orderExist = orderRepository.findById(orderId).orElseThrow(() ->
                new ResourceNotFoundException("order not found for this id: "+orderId));
        System.out.println(orderExist.getId());
        orderExist.setStatus("Đã hủy");
        Order order = orderRepository.save(orderExist);
        List<DetailOrderDTO> details = new DetailOrderDTO().entityToDTO(detailOrderRepository.findOrderDetailsByOrder(order));
        System.out.println("aaaa");
        List<Product> products = productrepository.findAll();
        System.out.println("22222");
        for(int i = 0; i< details.size(); i++) {
            for(int j=0; j< products.size(); j++) {
                if(details.get(i).getProduct_id() == products.get(j).getId()) {
                    products.get(j).setProduct_sold(products.get(j).getProduct_sold()-details.get(i).getDetail_qty());
                    products.get(j).setQty(products.get(j).getQty()+details.get(i).getDetail_qty());
                    productrepository.save(products.get(j));
                    System.out.println("bbbb");
                }

            }
        }
        return new OrderDTO().entityToDTO(order);
    }
    // Accept order
    @Override
    public OrderDTO acceptOrder(Long orderId) throws ResourceNotFoundException {
        Order orderExist = orderRepository.findById(orderId).orElseThrow(() ->
                new ResourceNotFoundException("order not found for this id: "+orderId));
        System.out.println(orderExist.getId());
        orderExist.setStatus("Đang giao");
        return new OrderDTO().entityToDTO(orderExist);
    }
    @Override
    public OrderDTO updateStatusOrder(Long orderId, String status) throws ResourceNotFoundException {
        Order orderExist = orderRepository.findById(orderId).orElseThrow(() ->
                new ResourceNotFoundException("order not found for this id: "+orderId));

        orderExist.setStatus(status);
        Order order = new Order();
        order = orderRepository.save(orderExist);

        return new OrderDTO().entityToDTO(order);
    }
}
