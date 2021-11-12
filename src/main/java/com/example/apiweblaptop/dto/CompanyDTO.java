package com.example.apiweblaptop.dto;

import com.example.apiweblaptop.entity.Company;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CompanyDTO {
    private Long id;
    private String company_name;
    private String company_type;
    private String company_address;
    private String company_phone;

    public CompanyDTO entityToDTO(Company company) {
        CompanyDTO dto  = new CompanyDTO();
        dto.setId(company.getId());
        dto.setCompany_name(company.getCompany_name());
        dto.setCompany_type(company.getCompany_type());
        dto.setCompany_address(company.getCompany_address());
        dto.setCompany_phone(company.getCompany_phone());

        return dto;
    }

    public Company dtoToEntity(CompanyDTO dto) {
        Company company = new Company();
        company.setId(dto.getId());
        company.setCompany_name(dto.getCompany_name());
        company.setCompany_type(dto.getCompany_type());
        company.setCompany_address(dto.getCompany_address());
        company.setCompany_phone(dto.getCompany_phone());
        return company;
    }

    public List<CompanyDTO> entityToDTO(List<Company> companies) {
        List<CompanyDTO> list = new ArrayList<>();
        companies.forEach(x-> list.add(entityToDTO(x)));
        return list;
    }

    public List<Company> dtoToEntity(List<CompanyDTO> dtos) {
        List<Company> list = new ArrayList<>();
        dtos.forEach(x -> list.add(dtoToEntity(x)));

        return list;
    }
}
