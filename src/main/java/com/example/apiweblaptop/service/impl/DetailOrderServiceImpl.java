package com.example.apiweblaptop.service.impl;

import com.example.apiweblaptop.dto.*;
import com.example.apiweblaptop.entity.DetailOrder;
import com.example.apiweblaptop.entity.Order;
import com.example.apiweblaptop.entity.Product;
import com.example.apiweblaptop.entity.User;
import com.example.apiweblaptop.exception.AddDataFail;
import com.example.apiweblaptop.exception.ResourceNotFoundException;
import com.example.apiweblaptop.repo.*;
import com.example.apiweblaptop.service.DetailOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DetailOrderServiceImpl implements DetailOrderService {
    @Autowired
    private DetailOrderRepository detailRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productrepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ImageProductRepository imageProductRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<DetailOrderDTO> retrieveOrderDetails() {
        List<DetailOrder> details = detailRepository.findAll();
        int count = 0;
        //count = details.size();
        List<Product> products = productrepository.findAll();


        System.out.print("=========" + count);
        return new DetailOrderDTO().entityToDTO(details);
    }

    @Override
    public Optional<DetailOrderDTO> getOrderDetail(Long detailId) throws ResourceNotFoundException {
        DetailOrder detail = detailRepository.findById(detailId).orElseThrow(() -> new ResourceNotFoundException("detail not found for this id: "+detailId));
        return Optional.of(new DetailOrderDTO().entityToDTO(detail));
    }

    @Override
    public DetailOrderDTO saveOrderDetail(DetailOrderDTO detailDTO) throws ResourceNotFoundException, AddDataFail {
        Order order = orderRepository.findById(detailDTO.getOrder_id()).orElseThrow(() ->
                new ResourceNotFoundException("order not found for this id: "+detailDTO.getOrder_id()));

        Product product = productrepository.findById(detailDTO.getProduct_id()).orElseThrow(() ->
                new ResourceNotFoundException("product not found for this id: "+detailDTO.getProduct_id()));

        DetailOrder detail = new DetailOrderDTO().dtoToEntity(detailDTO);
        if(detailDTO.getDetail_qty() > product.getQty()){
            throw new AddDataFail(""+ ErrorCode.ADD_ORDER_DETAIL_ERROR);
        } else {
            detail.setOrder(order);
            detail.setProduct(product);

            product.setQty(product.getQty() - detailDTO.getDetail_qty());
            product.setProduct_sold(product.getProduct_sold() + detailDTO.getDetail_qty());
            productrepository.save(product);
        }

        return new DetailOrderDTO().entityToDTO(detailRepository.save(detail));
    }

    @Override
    public Boolean deleteOrderDetail(Long detailId) throws ResourceNotFoundException {
        DetailOrder detail = detailRepository.findById(detailId).orElseThrow(() -> new ResourceNotFoundException("order detail not found for this id: " + detailId));
        this.detailRepository.delete(detail);
        return true;
    }

    @Override
    public DetailOrderDTO updateOrderDetail(Long id, DetailOrderDTO detailDTO) throws ResourceNotFoundException {
        DetailOrder detailExist = detailRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("order detail not found for this id: "+id));

        detailExist.setDetail_qty(detailDTO.getDetail_qty());
        detailExist.setDetail_price(detailDTO.getDetail_price());

        DetailOrder detail = new DetailOrder();
        detail = detailRepository.save(detailExist);
        return new DetailOrderDTO().entityToDTO(detail);
    }

    @Override
    public List<OrderDetailResponseDTO> findDetailByOrder(Long orderId) throws ResourceNotFoundException {
        Optional<Order> orderExist = orderRepository.findById(orderId);
        if(!orderExist.isPresent()){
            throw new ResourceNotFoundException(""+ ErrorCode.FIND_ORDER_ERROR);
        }
        Order order = orderExist.get();

        List<OrderDetailResponseDTO> list =new OrderDetailResponseDTO().toListDto(detailRepository.findOrderDetailsByOrder(order));
        List<ImageDTO> images = new ImageDTO().toListDto(imageProductRepository.findAll());
        for(int i=0; i< list.size(); i++) {
            List<ImageDTO> imgs = new ArrayList<>();
            for(int j=0; j< images.size(); j++) {
                if(list.get(i).getProduct_id() == images.get(j).getProduct_id()) {
                    imgs.add(images.get(j));
                }
            }
            list.get(i).setImage(imgs);
        }
//        List<OrderDetailResponseDTO> detailDTOS = new ArrayList<>();
//        detailDTOS = new OrderDetailResponseDTO().toListDto(list);
        return list;
    }

    @Override
    public DetailOrderDTO restoreQty(Long detailId) throws ResourceNotFoundException {
        DetailOrder detail1 = detailRepository.findById(detailId).orElseThrow(()-> new ResourceNotFoundException("product not found for this id: "+detailId));
        DetailOrderDTO detailDTO = new DetailOrderDTO().entityToDTO(detail1);
        Product product = productrepository.findById(detailDTO.getProduct_id()).orElseThrow(() ->
                new ResourceNotFoundException("product not found for this id: "+detailDTO.getProduct_id()));

        Order order = orderRepository.findById(detailDTO.getOrder_id()).orElseThrow(() ->
                new ResourceNotFoundException("order not found for this id: "+detailDTO.getOrder_id()));

        DetailOrder detail = new DetailOrderDTO().dtoToEntity(detailDTO);

        detail.setOrder(order);
        detail.setProduct(product);

        product.setQty(product.getQty() + detailDTO.getDetail_qty());

        productrepository.save(product);

        return new DetailOrderDTO().entityToDTO(detailRepository.save(detail));
    }

    @Override
    public List<Object> getTopProduct() {
        List<Object> topProducts = new ArrayList<>();
        Query query = null;
        query = entityManager.createNativeQuery("select p.product_id, p.product_name, subR.sum, subR.my_rank from ( select *, rank() over ( order by sub.sum desc) as my_rank \n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\tfrom ( select product_id, sum(detail_qty) from order_detail od group by product_id )as sub) as subR ,  product p\n" +
                "where p.product_id = subR.product_id and subR.my_rank <= 10 order by subR.my_rank" );
        topProducts = query.getResultList();
        int count = topProducts.size();
//        for (int i=0; i<=count; i++){
//            System.out.print(query.getParameterValue(i)+"------"+query.getParameter(i));
//            //topProducts.get(i).setProductName((String) query.getParameterValue(i));
//        }
        return topProducts;
    }

    @Override
    public Dashboard getDashboard() throws Exception {
        Dashboard dashboard = new Dashboard();
        try {
            List<Product> products = productrepository.findAll();
            List<User> users = userRepository.findAll();
            List<Order> orders = orderRepository.findAll();
            int countProduct = products.size();
            int countUser = users.size();
            int countOrder = orders.size();
            float price = 0;
            for (Order order : orders) {
                price = price + order.getTotal_price();
            }
            dashboard.setProduct(countProduct);
            dashboard.setUser(countUser);
            dashboard.setOrder(countOrder);
            dashboard.setTotalPrice(price);

        } catch (Exception e){
            throw new Exception();
        }
        return dashboard;
    }



}
