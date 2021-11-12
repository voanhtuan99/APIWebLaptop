package com.example.apiweblaptop.dto;

import com.example.apiweblaptop.entity.PhieuNhap;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PhieuNhapDTO {
    private Long id;
    private LocalDate ngaylapphieu;
    private String loaiphieu;
    private Long iduser;
    private Long idcompany;
    private String status;
    public PhieuNhapDTO entityToDTO(PhieuNhap phieuNhap) {
        PhieuNhapDTO dto = new PhieuNhapDTO();
        dto.setId(phieuNhap.getId());
        dto.setNgaylapphieu(phieuNhap.getNgaylapphieu());
        dto.setLoaiphieu(phieuNhap.getLoaiphieu());
        dto.setIduser(phieuNhap.getUser().getId());
        dto.setIdcompany(phieuNhap.getCompany().getId());
        dto.setStatus(phieuNhap.getStatus());
        return dto;
    }

    public PhieuNhap dtoToEntity(PhieuNhapDTO dto) {
        PhieuNhap phieuNhap = new PhieuNhap();
        phieuNhap.setId(dto.getId());
        phieuNhap.setNgaylapphieu(dto.getNgaylapphieu());
        phieuNhap.setLoaiphieu(dto.getLoaiphieu());
        phieuNhap.setStatus(dto.getStatus());
        return phieuNhap;
    }

    public List<PhieuNhap> dtoToEntity(List<PhieuNhapDTO> dtos) {
        List<PhieuNhap> list = new ArrayList<>();
        dtos.forEach(x -> list.add(dtoToEntity(x)));
        return list;
    }

    public List<PhieuNhapDTO> entityToDTO(List<PhieuNhap> phieuNhaps) {
        List<PhieuNhapDTO> list = new ArrayList<>();
        phieuNhaps.forEach(x-> list.add(entityToDTO(x)));

        return list;
    }
}
