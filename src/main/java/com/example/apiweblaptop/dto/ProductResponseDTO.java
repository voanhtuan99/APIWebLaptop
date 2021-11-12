package com.example.apiweblaptop.dto;

import com.example.apiweblaptop.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductResponseDTO {
    private long product_id;

    @NotBlank
    private String productName;

    @NotNull
    @Min(value = 1)
    private float productPrice;

    private float productDiscount;
    @NotBlank
    private String productDescription;

    @Min(value = 0)
    private Integer productQty;
    private String categoryName;
    private String brandName;

    private List<ImageDTO> imageDTOS;

    //private Set<Long> productRatings;
    private float ratingTB;


    public ProductResponseDTO(String productName, float productPrice, String productDescription, Integer productQty, String categoryName, String brandName) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.productQty = productQty;
        this.categoryName = categoryName;
        this.brandName = brandName;
    }

    public ProductResponseDTO convertToDto(Product product) {
        ProductResponseDTO productDTO = new ProductResponseDTO();
        productDTO.setProduct_id(product.getId());
        productDTO.setProductName(product.getProductName());
        productDTO.setProductPrice(product.getPrice());
        productDTO.setProductDescription(product.getDescription());
        productDTO.setProductQty(product.getQty());
        productDTO.setCategoryName(product.getCategory().getCategoryName());
        productDTO.setBrandName(product.getBrand().getBrandName());
        productDTO.setProductDiscount(product.getDiscount());
        List<ImageDTO> dtos = new ArrayList<>();

        if(product.getProductImages()!=null){
            product.getProductImages().forEach(e -> {
                dtos.add(new ImageDTO().convertToDto(e));
            });
        }
        productDTO.setImageDTOS(dtos);

        return productDTO;
    }

    public Product convertToEti(ProductResponseDTO productDTO) {
        Product product = new Product();

        product.setProductName(productDTO.getProductName());
        product.setPrice(productDTO.getProductPrice());
        product.setDescription(productDTO.getProductDescription());
        product.setQty(productDTO.getProductQty());

        return product;
    }


    public List<ProductResponseDTO> toListDto(List<Product> listEntity) {
        List<ProductResponseDTO> listDto = new ArrayList<>();

        listEntity.forEach(e->{
            listDto.add(this.convertToDto(e));
        });
        return listDto;
    }
}
