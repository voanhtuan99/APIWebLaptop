package com.example.apiweblaptop.dto;

import com.example.apiweblaptop.entity.CTPhieuNhap;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CTPhieuNhapDTO {
    private Long id;
    private Integer quantity;
    private Float price;
    private Long idimport;
    private Long idproduct;

    public CTPhieuNhapDTO entityToDTO(CTPhieuNhap ctPhieuNhap) {
        CTPhieuNhapDTO dto = new CTPhieuNhapDTO();
        dto.setId(ctPhieuNhap.getId());
        dto.setQuantity(ctPhieuNhap.getQuantity());
        dto.setPrice(ctPhieuNhap.getPrice());
        dto.setIdproduct(ctPhieuNhap.getProduct().getId());
        dto.setIdimport(ctPhieuNhap.getPhieuNhap().getId());
        return dto;
    }

    public CTPhieuNhap dtoToEntity(CTPhieuNhapDTO dto){
        CTPhieuNhap ctPhieuNhap = new CTPhieuNhap();
        ctPhieuNhap.setId(dto.getId());
        ctPhieuNhap.setQuantity(dto.getQuantity());
        ctPhieuNhap.setPrice(dto.getPrice());
        return ctPhieuNhap;
    }

    public List<CTPhieuNhapDTO> entityToDTO(List<CTPhieuNhap> ctPhieuNhaps) {
        List<CTPhieuNhapDTO> list = new ArrayList<>();
        ctPhieuNhaps.forEach(x-> list.add(entityToDTO(x)));
        return list;
    }

    public List<CTPhieuNhap> dtoToEntity(List<CTPhieuNhapDTO> dtos) {
        List<CTPhieuNhap> list = new ArrayList<>();
        dtos.forEach(x-> list.add(dtoToEntity(x)));
        return list;
    }
}
