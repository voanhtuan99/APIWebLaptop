package com.example.apiweblaptop.service.impl;


import com.example.apiweblaptop.dto.*;
import com.example.apiweblaptop.entity.CTPhieuNhap;
import com.example.apiweblaptop.entity.PhieuNhap;
import com.example.apiweblaptop.entity.Product;
import com.example.apiweblaptop.repo.CTPhieuNhapRepository;
import com.example.apiweblaptop.repo.PhieuNhapRepository;
import com.example.apiweblaptop.repo.ProductRepository;
import com.example.apiweblaptop.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {
    @Autowired
    PhieuNhapRepository phieuNhapRepository;
    @Autowired
    CTPhieuNhapRepository ctPhieuNhapRepository;
    @Autowired
    ProductRepository productRepository;
    @Override
    public Statistical TkeAll(DateDashboardDTO dateDashboardDTO) {
        List<PhieuNhap> phieuNhaps = phieuNhapRepository.findAll();
        List<CTPhieuNhapDTO> ctPhieuNhaps = new CTPhieuNhapDTO().entityToDTO(ctPhieuNhapRepository.findAll());
        LocalDate begin = LocalDate.parse(dateDashboardDTO.getDateBegin());
        LocalDate end = LocalDate.parse(dateDashboardDTO.getDateEnd());
        Statistical tke = new Statistical();
        tke.setTienLoi(Float.parseFloat("0"));
        tke.setTienNhap(Float.parseFloat("0"));
        tke.setTienXuat(Float.parseFloat("0"));
        tke.setSoLuongNhap(0);
        tke.setSoLuongXuat(0);
        phieuNhaps.forEach(e -> {
            if(e.getNgaylapphieu().isBefore(end) && e.getNgaylapphieu().isAfter(begin)) {
                ctPhieuNhaps.forEach(e1 -> {
                    if(e.getId() == e1.getIdimport()) {
                        if(e.getLoaiphieu().equals("Phiếu nhập") && e.getStatus().equals("Success")) {
                            tke.setSoLuongNhap(e1.getQuantity()+tke.getSoLuongNhap());
                            tke.setTienNhap(e1.getPrice()+tke.getTienNhap());
                        }
                        else if(e.getLoaiphieu().equals("Phiếu xuất") && e.getStatus().equals("Success")) {
                            tke.setSoLuongXuat(e1.getQuantity()+tke.getSoLuongXuat());
                            tke.setTienXuat(e1.getPrice()+tke.getTienXuat());
                        }
                    }
                });
            }
        });
        tke.setTienLoi(tke.getTienXuat()-tke.getTienNhap());
        return tke;
    }
    @Override
    public List<ProductSoldMaxDTO> top5Sold(DateDashboardDTO dateDashboardDTO) {
        List<PhieuNhap> phieuNhaps = phieuNhapRepository.findAll();
        List<CTPhieuNhapDTO> ctPhieuNhaps = new CTPhieuNhapDTO().entityToDTO(ctPhieuNhapRepository.findAll());
//        List<PhieuNhap> phieuNhap1 = new ArrayList<>();
        List<CTPhieuNhapDTO> ctPhieuNhaps1 = new ArrayList<>();
        List<Product> products = productRepository.findAll();
        LocalDate begin = LocalDate.parse(dateDashboardDTO.getDateBegin());
        LocalDate end = LocalDate.parse(dateDashboardDTO.getDateEnd());
        List<ProductSoldMaxDTO> productSold = new ArrayList<>();
        phieuNhaps.forEach(e -> {
            if (e.getNgaylapphieu().isBefore(end) && e.getNgaylapphieu().isAfter(begin) && e.getLoaiphieu().equals("Phiếu xuất") ) {
                ctPhieuNhaps.forEach(e1 -> {
                    if (e.getId() == e1.getIdimport()) {
                        ctPhieuNhaps1.add(e1);
                    }
                });
            }
        });

        products.forEach(e1 -> {
            ProductSoldMaxDTO productSoldMaxDTO = new ProductSoldMaxDTO();
            productSoldMaxDTO.setQuantitySold(0);
            ctPhieuNhaps1.forEach(e -> {
                if(e.getIdproduct() == e1.getId()) {
                    productSoldMaxDTO.setQuantitySold(productSoldMaxDTO.getQuantitySold()+e.getQuantity());
                }
            });
            productSoldMaxDTO.setProductName(e1.getProductName());
            productSold.add(productSoldMaxDTO);
        });
        List<ProductSoldMaxDTO> productSoldList = productSold.stream().sorted((o1, o2) -> {
            if(Integer.parseInt(String.valueOf(o1.getQuantitySold())) < Integer.parseInt(String.valueOf(o2.getQuantitySold())))
                return 1;
            else return -1;
        }).collect(Collectors.toList());
        List<ProductSoldMaxDTO> productReturn = new ArrayList<>();
        for(int i=0; i<5; i++) {
            productReturn.add(productSoldList.get(i));
        }
        return productReturn;
    }

    @Override
    public List<ProductSoldPriceDTO> top5Price(DateDashboardDTO dateDashboardDTO) {
        List<PhieuNhap> phieuNhaps = phieuNhapRepository.findAll();
        List<CTPhieuNhapDTO> ctPhieuNhaps = new CTPhieuNhapDTO().entityToDTO(ctPhieuNhapRepository.findAll());
//        List<PhieuNhap> phieuNhap1 = new ArrayList<>();
        List<CTPhieuNhapDTO> ctPhieuNhaps1 = new ArrayList<>();
        List<Product> products = productRepository.findAll();
        LocalDate begin = LocalDate.parse(dateDashboardDTO.getDateBegin());
        LocalDate end = LocalDate.parse(dateDashboardDTO.getDateEnd());
        List<ProductSoldPriceDTO> productSold = new ArrayList<>();
        phieuNhaps.forEach(e -> {
            if (e.getNgaylapphieu().isBefore(end) && e.getNgaylapphieu().isAfter(begin) && e.getLoaiphieu().equals("Phiếu xuất") ) {
                ctPhieuNhaps.forEach(e1 -> {
                    if (e.getId() == e1.getIdimport()) {
                        ctPhieuNhaps1.add(e1);
                    }
                });
            }
        });

        products.forEach(e1 -> {
            ProductSoldPriceDTO productSoldMaxDTO = new ProductSoldPriceDTO();
            productSoldMaxDTO.setPriceSold(Float.parseFloat("0"));
            ctPhieuNhaps1.forEach(e -> {
                if(e.getIdproduct() == e1.getId()) {
                    productSoldMaxDTO.setPriceSold(productSoldMaxDTO.getPriceSold()+e.getPrice()*e.getQuantity());
                }
            });
            productSoldMaxDTO.setProductName(e1.getProductName());
            productSold.add(productSoldMaxDTO);
        });
        List<ProductSoldPriceDTO> productSoldList = productSold.stream().sorted((o1, o2) -> {
            if(Float.parseFloat(String.valueOf(o1.getPriceSold())) < Float.parseFloat(String.valueOf(o2.getPriceSold())))
                return 1;
            else return -1;
        }).collect(Collectors.toList());
        List<ProductSoldPriceDTO> productReturn = new ArrayList<>();
        for(int i=0; i<5; i++) {
            productReturn.add(productSoldList.get(i));
        }
        return productReturn;
    }

    @Override
    public List<ProductSoldPriceDTO> statisticProfit(DateDashboardDTO dateDashboardDTO) {
        List<PhieuNhap> phieuNhaps = phieuNhapRepository.findAll();
        List<CTPhieuNhapDTO> ctPhieuNhaps = new CTPhieuNhapDTO().entityToDTO(ctPhieuNhapRepository.findAll());
//        List<PhieuNhap> phieuNhap1 = new ArrayList<>();
        List<CTPhieuNhapDTO> ctPhieuNhaps1 = new ArrayList<>();
        List<Product> products = productRepository.findAll();
        LocalDate begin = LocalDate.parse(dateDashboardDTO.getDateBegin());
        LocalDate end = LocalDate.parse(dateDashboardDTO.getDateEnd());
        List<ProductSoldPriceDTO> productSold = new ArrayList<>();
        phieuNhaps.forEach(e -> {
            if (e.getNgaylapphieu().isBefore(end) && e.getNgaylapphieu().isAfter(begin) && e.getLoaiphieu().equals("Phiếu xuất")) {
                ctPhieuNhaps.forEach(e1 -> {
                    if (e.getId() == e1.getIdimport()) {
                        ctPhieuNhaps1.add(e1);
                    }
                });
            }
        });

        products.forEach(e1 -> {
            ProductSoldPriceDTO productSoldMaxDTO = new ProductSoldPriceDTO();
            productSoldMaxDTO.setPriceSold(Float.parseFloat("0"));
            ctPhieuNhaps1.forEach(e -> {
                if(e.getIdproduct() == e1.getId()) {
                    productSoldMaxDTO.setPriceSold(productSoldMaxDTO.getPriceSold()+e.getPrice()*e.getQuantity());
                }
            });
            productSoldMaxDTO.setProductName(e1.getProductName());
            productSold.add(productSoldMaxDTO);
        });

        return productSold;
    }

    @Override
    public List<ProductSoldMaxDTO> statisticSoldProduct(DateDashboardDTO dateDashboardDTO) {
        List<PhieuNhap> phieuNhaps = phieuNhapRepository.findAll();
        List<CTPhieuNhapDTO> ctPhieuNhaps = new CTPhieuNhapDTO().entityToDTO(ctPhieuNhapRepository.findAll());
//        List<PhieuNhap> phieuNhap1 = new ArrayList<>();
        List<CTPhieuNhapDTO> ctPhieuNhaps1 = new ArrayList<>();
        List<Product> products = productRepository.findAll();
        LocalDate begin = LocalDate.parse(dateDashboardDTO.getDateBegin());
        LocalDate end = LocalDate.parse(dateDashboardDTO.getDateEnd());
        List<ProductSoldMaxDTO> productSold = new ArrayList<>();
        phieuNhaps.forEach(e -> {
            if (e.getNgaylapphieu().isBefore(end) && e.getNgaylapphieu().isAfter(begin) && e.getLoaiphieu().equals("Phiếu xuất")) {
                ctPhieuNhaps.forEach(e1 -> {
                    if (e.getId() == e1.getIdimport()) {
                        ctPhieuNhaps1.add(e1);
                    }
                });
            }
        });

        products.forEach(e1 -> {
            ProductSoldMaxDTO productSoldMaxDTO = new ProductSoldMaxDTO();
            productSoldMaxDTO.setQuantitySold(0);
            ctPhieuNhaps1.forEach(e -> {
                if(e.getIdproduct() == e1.getId()) {
                    productSoldMaxDTO.setQuantitySold(productSoldMaxDTO.getQuantitySold()+e.getQuantity());
                }
            });
            productSoldMaxDTO.setProductName(e1.getProductName());
            productSold.add(productSoldMaxDTO);
        });


        return productSold;
    }
}
