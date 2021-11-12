package com.example.apiweblaptop.service.impl;

import com.example.apiweblaptop.dto.ImageDTO;
import com.example.apiweblaptop.entity.Product;
import com.example.apiweblaptop.entity.ProductImage;
import com.example.apiweblaptop.exception.ResourceNotFoundException;
import com.example.apiweblaptop.repo.ImageProductRepository;
import com.example.apiweblaptop.repo.ProductRepository;
import com.example.apiweblaptop.service.ImageProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ImageProductServiceImpl implements ImageProductService {
    @Autowired
    private ImageProductRepository imageRepository;

    @Autowired
    private ProductRepository productrepository;

    @Override
    public List<ImageDTO> retrieveProductImages() {
        List<ProductImage> productImages = imageRepository.findAll();
        return new ImageDTO().toListDto(productImages);
    }

    @Override
    public Optional<ImageDTO> getProductImage(Long imageId) throws ResourceNotFoundException {
        ProductImage image = imageRepository.findById(imageId).orElseThrow(() -> new ResourceNotFoundException("image not found for this id: "+imageId));
        return Optional.of(new ImageDTO().convertToDto(image));
    }

//    @Override
//    public List<ImageDTO> getImageByProductId(Long productId) throws ResourceNotFoundException {
//        List<ImageDTO> dtos = new ArrayList<>();
//        List<ProductImage> productImages = imageRepository.findAll();
//        List<ImageDTO> newDtos = new ImageDTO().toListDto(productImages);
//        newDtos.forEach(e -> {
//            if(e.getProduct_id() == productId) {
//                dtos.add();
//            }
//        });
//    }

    @Override
    public ImageDTO saveProductImage(ImageDTO imageDTO) throws ResourceNotFoundException {
        Product product = productrepository.findById(imageDTO.getProduct_id()).orElseThrow(() ->
                new ResourceNotFoundException("product not found for this id: "+imageDTO.getProduct_id()));

        ProductImage image = new ImageDTO().convertToEti(imageDTO);
        image.setProduct(product);

        return new ImageDTO().convertToDto(imageRepository.save(image));
    }

    @Override
    public Boolean deleteProductImage(Long imageId) throws ResourceNotFoundException {
        ProductImage productImage = imageRepository.findById(imageId).orElseThrow(() -> new ResourceNotFoundException("iamge not found for this id: " + imageId));
        this.imageRepository.delete(productImage);
        return true;
    }

    @Override
    public ImageDTO updateProductImage(Long id, ImageDTO imageDTO) throws ResourceNotFoundException {
        ProductImage imageExist = imageRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("image not found for this id: "+id));

        imageExist.setImageLink(imageDTO.getImageLink());

        ProductImage image = new ProductImage();
        image = imageRepository.save(imageExist);
        return new ImageDTO().convertToDto(image);
    }
}
