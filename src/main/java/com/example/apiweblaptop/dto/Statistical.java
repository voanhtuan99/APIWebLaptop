package com.example.apiweblaptop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Statistical {
    private Float TienNhap;
    private Float TienXuat;
    private Integer SoLuongNhap;
    private Integer SoLuongXuat;
    private Float TienLoi;

    public void setTienNhap(Float tienNhap) {
        TienNhap = tienNhap;
    }

    public void setTienXuat(Float tienXuat) {
        TienXuat = tienXuat;
    }

    public void setSoLuongNhap(Integer soLuongNhap) {
        SoLuongNhap = soLuongNhap;
    }

    public void setSoLuongXuat(Integer soLuongXuat) {
        SoLuongXuat = soLuongXuat;
    }

    public void setTienLoi(Float tienLoi) {
        TienLoi = tienLoi;
    }

    public Float getTienNhap() {
        return TienNhap;
    }

    public Float getTienXuat() {
        return TienXuat;
    }

    public Integer getSoLuongNhap() {
        return SoLuongNhap;
    }

    public Integer getSoLuongXuat() {
        return SoLuongXuat;
    }

    public Float getTienLoi() {
        return TienLoi;
    }
}
