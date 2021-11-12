package com.example.apiweblaptop.service.impl;

import com.example.apiweblaptop.dto.CTPhieuNhapDTO;
import com.example.apiweblaptop.entity.CTPhieuNhap;
import com.example.apiweblaptop.entity.Company;
import com.example.apiweblaptop.entity.PhieuNhap;
import com.example.apiweblaptop.entity.Product;
import com.example.apiweblaptop.exception.ResourceNotFoundException;
import com.example.apiweblaptop.repo.CTPhieuNhapRepository;
import com.example.apiweblaptop.repo.PhieuNhapRepository;
import com.example.apiweblaptop.repo.ProductRepository;
import com.example.apiweblaptop.service.CTPhieuNhapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CTPhieuNhapServiceImpl implements CTPhieuNhapService {
    @Autowired
    private CTPhieuNhapRepository nhapRepository;

    @Autowired
    private PhieuNhapRepository phieuNhapRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<CTPhieuNhapDTO> retrieveCTPNs() {
        List<CTPhieuNhap> nhaps = nhapRepository.findAll();

        return new CTPhieuNhapDTO().entityToDTO(nhaps);
    }

    @Override
    public Optional<CTPhieuNhapDTO> getCTPN(Long ctpnId) throws ResourceNotFoundException {
        CTPhieuNhap nhap = nhapRepository.findById(ctpnId).orElseThrow(() -> new ResourceNotFoundException("phieu nhap not found for this id"+ctpnId));
        return Optional.of(new CTPhieuNhapDTO().entityToDTO(nhap));
    }

    @Override
    public CTPhieuNhapDTO saveCTPN(CTPhieuNhapDTO nhapDTO) throws ResourceNotFoundException {
        CTPhieuNhap nhap = new CTPhieuNhapDTO().dtoToEntity(nhapDTO);
        Product product = productRepository.findById(nhapDTO.getIdproduct()).orElseThrow(() ->
                new ResourceNotFoundException("product not found for this id"+nhapDTO.getIdproduct()));
        product.setQty(product.getQty() + nhapDTO.getQuantity());
        productRepository.save(product);
        PhieuNhap pn = phieuNhapRepository.getById(nhapDTO.getIdimport());
        nhap.setPhieuNhap(pn);
        nhap.setProduct(product);
        return new CTPhieuNhapDTO().entityToDTO(nhapRepository.save(nhap));
    }

    @Override
    public Boolean deleteCTPN(Long ctpnId) throws ResourceNotFoundException {
        CTPhieuNhap nhap = nhapRepository.findById(ctpnId).orElseThrow(() -> new ResourceNotFoundException("phieu nhap not found for this id"));
        this.nhapRepository.delete(nhap);
        return true;
    }

    @Override
    public CTPhieuNhapDTO updateCTPN(Long ctpnId) throws ResourceNotFoundException {
        CTPhieuNhap nhapExist = nhapRepository.findById(ctpnId).orElseThrow(() -> new ResourceNotFoundException("phieu nhap not found for this id"));
        Product product = productRepository.findById(nhapExist.getProduct().getId()).orElseThrow(()-> new ResourceNotFoundException("phieu nhap not found for this id"));

        if(product.getQty() > nhapExist.getQuantity()) {
            product.setQty(product.getQty() - nhapExist.getQuantity());
            productRepository.save(product);
        }
        else{
            System.out.println("Quantity is not enought");
        }
        PhieuNhap pn = phieuNhapRepository.getById(nhapExist.getPhieuNhap().getId());
        nhapExist.setPhieuNhap(pn);
        nhapExist.setProduct(product);
        CTPhieuNhap phieuNhap = new CTPhieuNhap();
        phieuNhap = nhapRepository.save(nhapExist);
        return new CTPhieuNhapDTO().entityToDTO(phieuNhap);
    }
}
