package com.example.apiweblaptop.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "import")
public class PhieuNhap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "ngaylapphieu")
    private LocalDate ngaylapphieu;
    @Column(name = "loaiphieu")
    private String loaiphieu;
    @JoinColumn(name = "status")
    private String status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iduser")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcompany")
    private Company company;

}
