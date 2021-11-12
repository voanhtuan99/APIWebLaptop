package com.example.apiweblaptop.entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "company_name")
    private String company_name;
    @Column(name = "company_type")
    private String company_type;
    @Column(name = "company_address")
    private String company_address;
    @Column(name = "company_phone")
    private String company_phone;
}
