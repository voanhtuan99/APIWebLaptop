package com.example.apiweblaptop.service;

import com.example.apiweblaptop.dto.InputSearch;
import com.example.apiweblaptop.dto.ProductDTO;
import com.example.apiweblaptop.dto.ProductResponseDTO;
import com.example.apiweblaptop.entity.Product;
import com.example.apiweblaptop.exception.BadRequestException;
import com.example.apiweblaptop.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    public List<ProductDTO> retrieveProducts();

    public List<ProductDTO> get4ProductNew();
    public List<ProductDTO> top4ProductHot() throws ResourceNotFoundException;

    public ProductDTO saveProduct(ProductDTO productDTO) throws ResourceNotFoundException, BadRequestException;

    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) throws ResourceNotFoundException;

    public Boolean deleteProduct(Long productId) throws ResourceNotFoundException;

    public ProductDTO getProduct(Long productId) throws ResourceNotFoundException;

    public List<ProductDTO> retrieveProductBrand(Long brandId);

    public List<ProductDTO> retrieveProductCate(Long cateId);

    public List<ProductDTO> searchProduct(InputSearch inputText);
}
