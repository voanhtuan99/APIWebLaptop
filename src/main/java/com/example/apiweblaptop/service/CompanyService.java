package com.example.apiweblaptop.service;

import com.example.apiweblaptop.dto.CategoryDTO;
import com.example.apiweblaptop.dto.CompanyDTO;
import com.example.apiweblaptop.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface CompanyService {
    public List<CompanyDTO> retrieveCompany();

    public List<CompanyDTO> getNpp();

    public List<CompanyDTO> getDVVC();

    public Optional<CompanyDTO> getCompany(Long id) throws ResourceNotFoundException;

    public CompanyDTO saveCompany(CompanyDTO companyDTO) throws ResourceNotFoundException;

    public Boolean deleteCompany(Long companyId) throws ResourceNotFoundException;

    public CompanyDTO updateCompany(Long companyId,CompanyDTO companyDTO) throws ResourceNotFoundException;
}
