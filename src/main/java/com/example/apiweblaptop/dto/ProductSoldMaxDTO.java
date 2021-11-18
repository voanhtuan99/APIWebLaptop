package com.example.apiweblaptop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductSoldMaxDTO {
    private String productName;
    private Integer quantitySold;
}
