package com.example.apiweblaptop.repo;

import com.example.apiweblaptop.dto.BrandDTO;
import com.example.apiweblaptop.entity.Brand;
import com.example.apiweblaptop.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long> {

}
