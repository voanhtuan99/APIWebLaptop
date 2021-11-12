package com.example.apiweblaptop.service.impl;

import com.example.apiweblaptop.dto.CategoryDTO;
import com.example.apiweblaptop.dto.CompanyDTO;
import com.example.apiweblaptop.dto.ErrorCode;
import com.example.apiweblaptop.entity.Category;
import com.example.apiweblaptop.entity.Company;
import com.example.apiweblaptop.exception.ResourceNotFoundException;
import com.example.apiweblaptop.repo.CompanyRepository;
import com.example.apiweblaptop.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public List<CompanyDTO> retrieveCompany() {
        List<Company> companies = companyRepository.findAll();
        return new CompanyDTO().entityToDTO(companies);
    }


    @Override
    public Optional<CompanyDTO> getCompany(Long companyId) throws ResourceNotFoundException {
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new ResourceNotFoundException(""+ ErrorCode.FIND_CATEGORY_ERROR));
        return Optional.of(new CompanyDTO().entityToDTO(company));
    }

    @Override
    public CompanyDTO saveCompany(CompanyDTO companyDTO) throws ResourceNotFoundException {
        Company company = new CompanyDTO().dtoToEntity(companyDTO);
        return new CompanyDTO().entityToDTO(companyRepository.save(company));
    }

    @Override
    public Boolean deleteCompany(Long companyId) throws ResourceNotFoundException {
        Company company = companyRepository.findById(companyId).orElseThrow(() ->
                new ResourceNotFoundException(""+ ErrorCode.FIND_CATEGORY_ERROR));
        this.companyRepository.delete(company);
        return true;
    }

    @Override
    public CompanyDTO updateCompany(Long companyId, CompanyDTO companyDTO) throws ResourceNotFoundException {
        Company companyExist = companyRepository.findById(companyId).orElseThrow(() ->
                new ResourceNotFoundException(""+ ErrorCode.FIND_CATEGORY_ERROR));
        companyExist.setCompany_name(companyDTO.getCompany_name());
        companyExist.setCompany_type(companyDTO.getCompany_type());
        companyExist.setCompany_address(companyDTO.getCompany_address());
        companyExist.setCompany_phone(companyDTO.getCompany_phone());

        Company company = new Company();
        company = companyRepository.save(companyExist);
        return new CompanyDTO().entityToDTO(company);
    }


}
