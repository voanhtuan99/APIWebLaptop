package com.example.apiweblaptop.dto;

import com.example.apiweblaptop.entity.Brand;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class BrandDTO {
    private Long id;
    private String brand_name;

    public BrandDTO entityToDTO(Brand brand) {
        BrandDTO dto = new BrandDTO();
        dto.setId(brand.getId());
        dto.setBrand_name(brand.getBrandName());
        return dto;
    }

    public Brand dtoToEntity(BrandDTO dto){
        Brand branch  = new Brand();
        branch.setId(dto.getId());
        branch.setBrandName(dto.getBrand_name());
        return branch;
    }

    public List<BrandDTO> entityToDTO(List<Brand> branches) {
        return branches.stream().map(x -> entityToDTO(x)).collect(Collectors.toList());
    }

    public List<Brand> dtoToEntity(List<BrandDTO> dtos) {
        return dtos.stream().map(x -> dtoToEntity(x)).collect(Collectors.toList());
    }
}
