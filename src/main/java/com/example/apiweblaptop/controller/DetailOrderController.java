package com.example.apiweblaptop.controller;

import com.example.apiweblaptop.dto.*;
import com.example.apiweblaptop.exception.*;
import com.example.apiweblaptop.service.DetailOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "api/detailorder")
public class DetailOrderController {
    @Autowired
    private DetailOrderService detailService;

    @GetMapping("")
    public ResponseEntity<ResponseDTO> getAllOrderDetail() throws GetDataFail {
        ResponseDTO response = new ResponseDTO();
        List<ResponseDTO> responseDTO = new ArrayList<>();
        try {
            List<DetailOrderDTO> details = detailService.retrieveOrderDetails();
            List list = Collections.synchronizedList(new ArrayList(details));

            if (responseDTO.addAll(list) == true) {
                response.setData(details);
            }
            response.setSuccessCode(SuccessCode.GET_ALL_ORDER_DETAIL_SUCCESS);
        } catch (Exception e){
            throw new GetDataFail(""+ ErrorCode.GET_ORDER_DETAIL_ERROR);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{detail_id}")
    public ResponseEntity<ResponseDTO> findOrderDetail(@PathVariable("detail_id") Long detailId) throws ResourceNotFoundException {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Optional<DetailOrderDTO> detailDTO = detailService.getOrderDetail(detailId);

            responseDTO.setData(detailDTO);
            responseDTO.setSuccessCode(SuccessCode.FIND_ORDER_DETAIL_SUCCESS);
        } catch (Exception e){
            throw new ResourceNotFoundException(""+ErrorCode.FIND_ORDER_DETAIL_ERROR);
        }
        return ResponseEntity.ok(responseDTO);
    }

    //save
    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> createOrderDetail(@Valid @RequestBody DetailOrderDTO detailDTO) throws AddDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            DetailOrderDTO dto = detailService.saveOrderDetail(detailDTO);
            responseDTO.setData(dto);
            responseDTO.setSuccessCode(SuccessCode.ADD_ORDER_DETAIL_SUCCESS);
        } catch (Exception e){
            throw new AddDataFail(""+ErrorCode.ADD_ORDER_DETAIL_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }
    //
//    //update
    @PutMapping("/odetail/{detail_id}")
    public ResponseEntity<ResponseDTO> updateOrderDetail(@PathVariable(value = "detail_id") Long detailId,
                                                         @Valid @RequestBody DetailOrderDTO detailDTO) throws UpdateDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            DetailOrderDTO updateDetail = detailService.updateOrderDetail(detailId, detailDTO);

            responseDTO.setData(updateDetail);
            responseDTO.setSuccessCode(SuccessCode.UPDATE_ORDER_DETAIL_SUCCESS);
        } catch (Exception e){
            throw new UpdateDataFail(""+ErrorCode.UPDATE_ORDER_DETAIL_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }
    //
//    //delete
    @DeleteMapping("/odetail/{detail_id}")
    public ResponseEntity<ResponseDTO> deleteOrderDetail(@PathVariable(value = "detail_id") Long detailId) throws DeleteDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Boolean isDel = detailService.deleteOrderDetail(detailId);
            responseDTO.setData(isDel);
            responseDTO.setSuccessCode(SuccessCode.DELETE_ORDER_DETAIL_SUCCESS);
        } catch (Exception e){
            throw new DeleteDataFail(""+ErrorCode.DELETE_ORDER_DETAIL_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/order/{order_id}")
    public ResponseEntity<ResponseDTO> findDetailByOrder(@PathVariable("order_id") @NotBlank Long orderId) throws ResourceNotFoundException {
        ResponseDTO responseDTO = new ResponseDTO();
        List<OrderDetailResponseDTO> detailDTOS = detailService.findDetailByOrder(orderId);
        responseDTO.setData(detailDTOS);
        responseDTO.setSuccessCode(SuccessCode.FIND_ORDER_DETAIL_SUCCESS);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/odetail/qty/{product_id}")
    public ResponseEntity<ResponseDTO> restoreQty(@PathVariable(value = "product_id") Long productId) throws UpdateDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            DetailOrderDTO updateDetail = detailService.restoreQty(productId);
            responseDTO.setData(updateDetail);
            responseDTO.setSuccessCode(SuccessCode.UPDATE_ORDER_DETAIL_SUCCESS);
        } catch (Exception e){
            throw new UpdateDataFail(""+ErrorCode.UPDATE_ORDER_DETAIL_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/top/product")
    public ResponseEntity<ResponseDTO> getTopProduct() {
        ResponseDTO responseDTO = new ResponseDTO();
        List<Object> objects = detailService.getTopProduct();
        responseDTO.setData(objects);
        responseDTO.setSuccessCode(SuccessCode.GET_TOP_PRODUCT_SUCCESS);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<ResponseDTO> getDashboard() throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        Dashboard dashboard = detailService.getDashboard();
        responseDTO.setData(dashboard);
        responseDTO.setSuccessCode(SuccessCode.GET_DASHBOARD_SUCCESS);

        return ResponseEntity.ok(responseDTO);
    }
}
