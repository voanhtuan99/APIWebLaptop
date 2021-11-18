package com.example.apiweblaptop.service;

import com.example.apiweblaptop.dto.DateDashboardDTO;
import com.example.apiweblaptop.dto.ProductSoldMaxDTO;
import com.example.apiweblaptop.dto.ProductSoldPriceDTO;
import com.example.apiweblaptop.dto.Statistical;

import java.util.List;

public interface DashboardService {
    public Statistical TkeAll(DateDashboardDTO dateDashboardDTO);
    public List<ProductSoldMaxDTO> top5Sold(DateDashboardDTO dateDashboardDTO);
    public List<ProductSoldPriceDTO> top5Price(DateDashboardDTO dateDashboardDTO);
    public List<ProductSoldMaxDTO> statisticSoldProduct(DateDashboardDTO dateDashboardDTO);
    public List<ProductSoldPriceDTO> statisticProfit(DateDashboardDTO dateDashboardDTO);
}
