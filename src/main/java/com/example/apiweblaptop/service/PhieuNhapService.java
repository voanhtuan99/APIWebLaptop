package com.example.apiweblaptop.service;

import com.example.apiweblaptop.dto.PhieuNhapDTO;
import com.example.apiweblaptop.dto.PhieuNhapResponseDTO;
import com.example.apiweblaptop.exception.ResourceNotFoundException;

import java.util.List;

public interface PhieuNhapService {
    public List<PhieuNhapResponseDTO> retrievePhieuNhaps();

//    public PhieuNhapResponseDTO getPhieuNhap(Long nhapId) throws ResourceNotFoundException;

    public PhieuNhapResponseDTO savePN(PhieuNhapDTO nhapDTO) throws ResourceNotFoundException;

    public PhieuNhapResponseDTO savePX(PhieuNhapDTO nhapDTO) throws ResourceNotFoundException;


    public Boolean deletePN(Long nhapId) throws ResourceNotFoundException;

    public PhieuNhapDTO updatePN(PhieuNhapDTO dto, Long id) throws ResourceNotFoundException;

    public PhieuNhapDTO cancelPN(Long id) throws ResourceNotFoundException;

    public PhieuNhapResponseDTO getPhieuNhap(Long nhapId) throws ResourceNotFoundException;
//    public PhieuNhapDTO updateStatusPN(Long nhapId, String status) throws ResourceNotFoundException;
}
