package com.example.apiweblaptop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Dashboard {
    private int product;
    private int user;
    private int order;
    private float totalPrice;
}
