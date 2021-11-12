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
public class CTPhieuNhapResponseDTO {
    private Long id;
    private int qtyNhap;
    private float priceNhap;
    private String productName;
    private List<ImageDTO> imageDTOS;

    public CTPhieuNhapResponseDTO convertToDto(CTPhieuNhap detail) {
        CTPhieuNhapResponseDTO deatailDTO = new CTPhieuNhapResponseDTO();

        deatailDTO.setId(detail.getId());
        deatailDTO.setQtyNhap(detail.getQuantity());
        deatailDTO.setPriceNhap(detail.getPrice());

        return deatailDTO;
    }

    public CTPhieuNhap convertToEti(CTPhieuNhapResponseDTO deatailDTO) {
        CTPhieuNhap detail = new CTPhieuNhap();

        detail.setId(deatailDTO.getId());
        detail.setQuantity(deatailDTO.getQtyNhap());
        detail.setPrice(deatailDTO.getPriceNhap());

        return detail;
    }


    public List<CTPhieuNhapResponseDTO> toListDto(List<CTPhieuNhap> listEntity) {
        List<CTPhieuNhapResponseDTO> listDto = new ArrayList<>();

        listEntity.forEach(e->{
            listDto.add(this.convertToDto(e));
        });
        return listDto;
    }
}
