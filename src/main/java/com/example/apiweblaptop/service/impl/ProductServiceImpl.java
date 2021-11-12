package com.example.apiweblaptop.service.impl;


import com.example.apiweblaptop.dto.ErrorCode;
import com.example.apiweblaptop.dto.ImageDTO;
import com.example.apiweblaptop.dto.ProductDTO;
import com.example.apiweblaptop.dto.ProductResponseDTO;
import com.example.apiweblaptop.entity.Brand;
import com.example.apiweblaptop.entity.Category;
import com.example.apiweblaptop.entity.Product;
import com.example.apiweblaptop.entity.ProductImage;
import com.example.apiweblaptop.exception.BadRequestException;
import com.example.apiweblaptop.exception.ResourceNotFoundException;
import com.example.apiweblaptop.repo.BrandRepository;
import com.example.apiweblaptop.repo.CategoryRepository;
import com.example.apiweblaptop.repo.ImageProductRepository;
import com.example.apiweblaptop.repo.ProductRepository;
import com.example.apiweblaptop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BrandRepository branchRepository;
    @Autowired
    private ImageProductRepository imageProductRepository;

    @Override
    public List<ProductDTO> retrieveProducts() {
        List<ProductDTO> products = new ProductDTO().entityToDTO(productRepository.findAll());
        List<ImageDTO> images = new ImageDTO().toListDto(imageProductRepository.findAll());
        for(int i=0; i<products.size(); i++) {
            List<ImageDTO> imgs = new ArrayList<>();
            for(int j=0; j<images.size(); j++) {
                if(products.get(i).getId() == images.get(j).getProduct_id()) {
                    imgs.add(images.get(j));
                }
            }
            if(imgs.size()!=0) {
                products.get(i).setImageDTOS(imgs);
            }
            else {

            }
        }
        return products;
    }
    @Override
    public List<ProductDTO> top4ProductHot() throws ResourceNotFoundException {
        List<ProductDTO> productDTOS = new ProductDTO().entityToDTO(productRepository.findAll());
        List<ProductDTO> sortList = productDTOS.stream().sorted(Comparator.comparingInt(ProductDTO::getProduct_sold).reversed()).collect(Collectors.toList());
        List<ProductDTO> top4 = new ArrayList<>();
        System.out.println("qiaia");
        for(int i=0; i<4; i++) {
            top4.add(sortList.get(i));
        }
        List<ImageDTO> images = new ImageDTO().toListDto(imageProductRepository.findAll());
        for(int i=0; i<top4.size(); i++) {
            List<ImageDTO> imgs = new ArrayList<>();
            for(int j=0; j<images.size(); j++) {
                if(top4.get(i).getId() == images.get(j).getProduct_id()) {
                    imgs.add(images.get(j));
                }
            }
            if(imgs.size()!=0) {
                top4.get(i).setImageDTOS(imgs);
            }
            else {

            }
        }
        return top4;
    }

    @Override
    public List<ProductDTO> get4ProductNew() {
//        List<ProductDTO> productDTOS = new ProductDTO().entityToDTO(productRepository.findAll());
        List<ProductDTO> sortList = new ProductDTO().entityToDTO(productRepository.findAll().stream().sorted((o1, o2) -> {
            if(Integer.parseInt(String.valueOf(o1.getId())) > Integer.parseInt(String.valueOf(o2.getId())))
                return 1;
            else return -1;
        }).collect(Collectors.toList()));
        List<ProductDTO> top4 = new ArrayList<>();
        for(int i=0; i<4; i++) {
            top4.add(sortList.get(sortList.size()-i-1));
        }
        List<ImageDTO> images = new ImageDTO().toListDto(imageProductRepository.findAll());
        for(int i=0; i<top4.size(); i++) {
            List<ImageDTO> imgs = new ArrayList<>();
            for(int j=0; j<images.size(); j++) {
                if(top4.get(i).getId() == images.get(j).getProduct_id()) {
                    imgs.add(images.get(j));
                }
            }
            if(imgs.size()!=0) {
                top4.get(i).setImageDTOS(imgs);
            }
            else {

            }
        }
        return top4;
    }
    @Override
    public ProductDTO getProduct(Long productId) throws ResourceNotFoundException {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(""+ErrorCode.FIND_PRODUCT_ERROR));
        ProductDTO dto = new ProductDTO().entityToDTO(product);
        List<ImageDTO> images = new ImageDTO().toListDto(imageProductRepository.findAll());
        List<ImageDTO> imgs = new ArrayList<>();
        for(int j=0; j<images.size(); j++) {
            if(dto.getId() == images.get(j).getProduct_id()) {
                imgs.add(images.get(j));
            }
        }
        dto.setImageDTOS(imgs);
        return dto;
    }

    @Override
    public ProductDTO saveProduct(ProductDTO productDTO) throws ResourceNotFoundException, BadRequestException {
        Product product = new ProductDTO().dtoToEntity(productDTO);

        Category category = categoryRepository.findById(productDTO.getCategory_id()).orElseThrow(() -> new ResourceNotFoundException("Loi category"));

        product.setCategory(category);

        Brand branch = branchRepository.findById(productDTO.getBrand_id()).orElseThrow(() -> new ResourceNotFoundException("Loi branch"));
        product.setBrand(branch);
        product.setQty(0);
        product.setProduct_sold(0);
        product.setCreateAt(java.time.LocalDate.now());
            product =productRepository.save(product);
            List<ProductImage> images = new ImageDTO().toListEntity(productDTO.getImageDTOS());
            Product productFinal = product;
            List<ImageDTO> imageDtos = new ArrayList<>();
            images.forEach(i -> {
                i.setProduct(productFinal);
                //System.out.println("--------------------------"+ i.getImageLink()+"_"+i.getProduct().getProduct_id());
                ProductImage img = imageProductRepository.save(i);
                imageDtos.add(new ImageDTO().convertToDto(img));
            });
            ProductDTO proDTO = new ProductDTO().entityToDTO(product);
            proDTO.setImageDTOS(imageDtos);


        System.out.println("aaaaa " + product.getProductImages());
//        return new ProductDTO().entityToDTO(productRepository.findById(product.getId()).orElseThrow(()-> new ResourceNotFoundException("Them that bai")));
        return proDTO;
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) throws ResourceNotFoundException {
        Product proExist = productRepository.findById(productId).orElseThrow(() ->
                new ResourceNotFoundException(""+ErrorCode.FIND_PRODUCT_ERROR));

        Category category = categoryRepository.findById(productDTO.getCategory_id()).orElseThrow(() ->
                new ResourceNotFoundException(""+ ErrorCode.FIND_CATEGORY_ERROR));

        Brand branch = branchRepository.findById(productDTO.getBrand_id()).orElseThrow(()->
                new ResourceNotFoundException(""+ErrorCode.FIND_BRAND_ERROR));
        proExist.setProductName(productDTO.getProduct_name());
        proExist.setPrice(productDTO.getProduct_price());
        proExist.setDiscount(productDTO.getProduct_discount());
        proExist.setDescription(productDTO.getProduct_description());
        proExist.setBrand(branch);
        proExist.setCategory(category);
//        proExist.setCreateAt(proExist.getCreateAt());
        Product product = new Product();
        product = productRepository.save(proExist);

        return new ProductDTO().entityToDTO(product);
    }
    @Override
    public Boolean deleteProduct(Long productId) throws ResourceNotFoundException {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(""+ErrorCode.FIND_PRODUCT_ERROR));
        this.productRepository.delete(product);
        return true;
    }

}
