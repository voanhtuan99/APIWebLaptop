package com.example.apiweblaptop.service.impl;

import com.example.apiweblaptop.dto.BrandDTO;
import com.example.apiweblaptop.dto.ErrorCode;
import com.example.apiweblaptop.entity.Brand;
import com.example.apiweblaptop.exception.ResourceNotFoundException;
import com.example.apiweblaptop.repo.BrandRepository;
import com.example.apiweblaptop.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandRepository brandRepository;


    @Override
    public List<BrandDTO> retrieveBrands() {
        List<Brand> brands = brandRepository.findAll();
        return new BrandDTO().entityToDTO(brands);
    }

    @Override
    public Optional<BrandDTO> getBrand(Long brandId) throws ResourceNotFoundException {
        Brand brand = brandRepository.findById(brandId).orElseThrow(() -> new ResourceNotFoundException(""+ ErrorCode.FIND_BRAND_ERROR));
        return Optional.of(new BrandDTO().entityToDTO(brand));
    }

    @Override
    public BrandDTO saveBrand(BrandDTO brandDTO) {
        Brand brand = new BrandDTO().dtoToEntity(brandDTO);
        return new BrandDTO().entityToDTO(brandRepository.save(brand));
    }

    @Override
    public Boolean deleteBrand(Long brandId) throws ResourceNotFoundException {
        Brand brand = brandRepository.findById(brandId).orElseThrow(() -> new ResourceNotFoundException(""+ ErrorCode.FIND_BRAND_ERROR));
        this.brandRepository.delete(brand);
        return true;
    }

    @Override
    public BrandDTO updateBrand(Long brandId, BrandDTO brandDTO) throws ResourceNotFoundException {
        Brand brandExist = brandRepository.findById(brandId).orElseThrow(() -> new ResourceNotFoundException(""+ ErrorCode.FIND_BRAND_ERROR));

        brandExist.setBrandName(brandDTO.getBrand_name());

        Brand brand = new Brand();
        brand = brandRepository.save(brandExist);
        return new BrandDTO().entityToDTO(brand);
    }
}
