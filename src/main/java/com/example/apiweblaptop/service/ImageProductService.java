package com.example.apiweblaptop.service;

import com.example.apiweblaptop.dto.ImageDTO;
import com.example.apiweblaptop.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface ImageProductService {
    public List<ImageDTO> retrieveProductImages();
    public Optional<ImageDTO> getProductImage(Long imageId) throws ResourceNotFoundException;
    public ImageDTO saveProductImage(ImageDTO imageDTO) throws ResourceNotFoundException;
    public Boolean deleteProductImage(Long imageId) throws ResourceNotFoundException;
    public ImageDTO updateProductImage(Long id, ImageDTO imageDTO) throws ResourceNotFoundException;
//    public List<ImageDTO> getImageByProductId(Long productId) throws ResourceNotFoundException;
}
