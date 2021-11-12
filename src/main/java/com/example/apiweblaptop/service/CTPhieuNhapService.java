package com.example.apiweblaptop.service;

import com.example.apiweblaptop.dto.CTPhieuNhapDTO;
import com.example.apiweblaptop.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface CTPhieuNhapService {
    public List<CTPhieuNhapDTO> retrieveCTPNs();

    public Optional<CTPhieuNhapDTO> getCTPN(Long ctpnId) throws ResourceNotFoundException;

    public CTPhieuNhapDTO saveCTPN(CTPhieuNhapDTO nhapDTO) throws ResourceNotFoundException;

    public Boolean deleteCTPN(Long ctpnId) throws ResourceNotFoundException;

    public CTPhieuNhapDTO updateCTPN(Long ctpnId) throws ResourceNotFoundException;
}
