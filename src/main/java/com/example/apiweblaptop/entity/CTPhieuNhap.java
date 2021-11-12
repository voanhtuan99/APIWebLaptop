package com.example.apiweblaptop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "detailsimport")
public class CTPhieuNhap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "price")
    private Float price;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idimport")
    private PhieuNhap phieuNhap;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idproduct")
    private Product product;
}
