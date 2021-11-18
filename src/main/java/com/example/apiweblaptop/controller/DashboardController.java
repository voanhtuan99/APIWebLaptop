package com.example.apiweblaptop.controller;
import com.example.apiweblaptop.dto.*;
import com.example.apiweblaptop.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;
    @PostMapping("/tkeall")
    public ResponseEntity<ResponseDTO> getAll(@RequestBody DateDashboardDTO dateDashboardDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
            Statistical dto = dashboardService.TkeAll(dateDashboardDTO);
            responseDTO.setData(dto);
            responseDTO.setSuccessCode(SuccessCode.ADD_CT_PHIEU_NHAP_SUCCESS);


        return ResponseEntity.ok(responseDTO);
    }
    @PostMapping("/top5sold")
    public ResponseEntity<ResponseDTO> top5Sold(@RequestBody DateDashboardDTO dateDashboardDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        List<ProductSoldMaxDTO> dto = dashboardService.top5Sold(dateDashboardDTO);
        responseDTO.setData(dto);
        responseDTO.setSuccessCode(SuccessCode.ADD_CT_PHIEU_NHAP_SUCCESS);


        return ResponseEntity.ok(responseDTO);
    }
    @PostMapping("/top5profit")
    public ResponseEntity<ResponseDTO> top5SoldPrice(@RequestBody DateDashboardDTO dateDashboardDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        List<ProductSoldPriceDTO> dto = dashboardService.top5Price(dateDashboardDTO);
        responseDTO.setData(dto);
        responseDTO.setSuccessCode(SuccessCode.ADD_CT_PHIEU_NHAP_SUCCESS);


        return ResponseEntity.ok(responseDTO);
    }
    // thong ke so luong san pham ban duoc trong thang
    @PostMapping("/statisticsold")
    public ResponseEntity<ResponseDTO> statisticSold(@RequestBody DateDashboardDTO dateDashboardDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        List<ProductSoldMaxDTO> dto = dashboardService.statisticSoldProduct(dateDashboardDTO);
        responseDTO.setData(dto);
        responseDTO.setSuccessCode(SuccessCode.ADD_CT_PHIEU_NHAP_SUCCESS);


        return ResponseEntity.ok(responseDTO);
    }
    @PostMapping("/statisticprofit")
    public ResponseEntity<ResponseDTO> statisticPrice(@RequestBody DateDashboardDTO dateDashboardDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        List<ProductSoldPriceDTO> dto = dashboardService.statisticProfit(dateDashboardDTO);
        responseDTO.setData(dto);
        responseDTO.setSuccessCode(SuccessCode.ADD_CT_PHIEU_NHAP_SUCCESS);


        return ResponseEntity.ok(responseDTO);
    }
}
