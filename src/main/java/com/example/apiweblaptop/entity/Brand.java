package com.example.apiweblaptop.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "brand")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "brand_name")
    private String brandName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "brand")
    private Set<Product> products;
}
