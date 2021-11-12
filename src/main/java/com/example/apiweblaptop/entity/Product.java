package com.example.apiweblaptop.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "product_qty")
    private Integer qty;
    @Column(name="product_price")
    private float price;
    @Column(name = "product_discount")
    private float discount;
    @Column(name = "product_sold")
    private Integer product_sold;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    @Column(name = "product_description")
    private String description;
    @Column(name = "createAt")
    private LocalDate createAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="brand_id")
    private Brand brand;
    @OneToMany(fetch = FetchType.LAZY)
    private Set<DetailOrder> detailOrders;
    @OneToMany(fetch = FetchType.LAZY)
    private Set<ProductImage> productImages;
}
