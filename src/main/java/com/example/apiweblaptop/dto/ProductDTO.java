package com.example.apiweblaptop.dto;

import com.example.apiweblaptop.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String product_name;
    private Integer product_qty;
    private float product_price;
    private float product_discount;
    private String product_description;
    private Integer product_sold;
    private Long category_id;
    private Long brand_id;
    private LocalDate createAt;
    private List<ImageDTO> imageDTOS;
    public ProductDTO entityToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setProduct_name(product.getProductName());
        dto.setProduct_qty(product.getQty());
        dto.setProduct_price(product.getPrice());
        dto.setProduct_discount(product.getDiscount());
        dto.setProduct_description(product.getDescription());
        dto.setCategory_id(product.getCategory().getId());
        dto.setBrand_id(product.getBrand().getId());
        dto.setProduct_sold(product.getProduct_sold());
        dto.setCreateAt(product.getCreateAt());
        List<ImageDTO> listDto = new ArrayList<>();
        if(product.getProductImages()!=null){
            product.getProductImages().forEach(e -> {
                listDto.add(new ImageDTO().convertToDto(e));
            });
        }
        dto.setImageDTOS(listDto);

        return dto;
    }

    public List<ProductDTO> entityToDTO(List<Product> products) {
        return products.stream().map(x-> entityToDTO(x)).collect(Collectors.toList());
    }

    public Product dtoToEntity(ProductDTO dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setProductName(dto.getProduct_name());
        product.setQty(dto.getProduct_qty());
        product.setPrice(dto.getProduct_price());
        product.setDiscount(dto.getProduct_discount());
        product.setDescription(dto.getProduct_description());
        product.setProduct_sold(dto.getProduct_sold());
        product.setCreateAt(dto.getCreateAt());
        return product;
    }
}
