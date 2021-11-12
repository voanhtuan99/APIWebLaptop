package com.example.apiweblaptop.repo;

import com.example.apiweblaptop.entity.CTPhieuNhap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CTPhieuNhapRepository extends JpaRepository<CTPhieuNhap, Long> {
//    List<CTPhieuNhap> findAllByCtpnIdNhapId(long nhapId);
//    CTPhieuNhap findByCtpnIdNhapId(long nhapId);
}
