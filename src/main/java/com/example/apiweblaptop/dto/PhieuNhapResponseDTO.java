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
public class PhieuNhapResponseDTO {
    private Long nhapId;
    private LocalDate createDate;
    private String email;
    private String statusName;
    private Long company_id;
    private List<CTPhieuNhapResponseDTO> ctpNhapResponseDTOS;


    public PhieuNhapResponseDTO convertToDto(PhieuNhap phieuNhap) {
        PhieuNhapResponseDTO nhapDTO = new PhieuNhapResponseDTO();
        nhapDTO.setNhapId(phieuNhap.getId());
        nhapDTO.setCreateDate(phieuNhap.getNgaylapphieu());
        nhapDTO.setEmail(phieuNhap.getUser().getEmail());
        //nhapDTO.setDatId(phieuNhap.getPhieuDat().getDatId());
        nhapDTO.setStatusName(phieuNhap.getLoaiphieu());
        nhapDTO.setCompany_id(phieuNhap.getCompany().getId());

        return nhapDTO;
    }

    public PhieuNhap convertToEti(PhieuNhapResponseDTO nhapDTO) {
        PhieuNhap nhap = new PhieuNhap();

        nhap.setNgaylapphieu(LocalDate.now());

        return nhap;
    }


    public List<PhieuNhapResponseDTO> toListDto(List<PhieuNhap> listEntity) {
        List<PhieuNhapResponseDTO> listDto = new ArrayList<>();

        listEntity.forEach(e->{
            listDto.add(this.convertToDto(e));
        });
        return listDto;
    }
}
