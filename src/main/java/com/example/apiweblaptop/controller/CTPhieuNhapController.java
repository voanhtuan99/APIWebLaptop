package com.example.apiweblaptop.controller;

import com.example.apiweblaptop.dto.CTPhieuNhapDTO;
import com.example.apiweblaptop.dto.ErrorCode;
import com.example.apiweblaptop.dto.ResponseDTO;
import com.example.apiweblaptop.dto.SuccessCode;
import com.example.apiweblaptop.exception.AddDataFail;
import com.example.apiweblaptop.exception.DeleteDataFail;
import com.example.apiweblaptop.exception.GetDataFail;
import com.example.apiweblaptop.exception.UpdateDataFail;
import com.example.apiweblaptop.service.CTPhieuNhapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/ctpnhaps")
public class CTPhieuNhapController {
    @Autowired
    private CTPhieuNhapService ctPhieuNhapService;

    @GetMapping("")
    public ResponseEntity<ResponseDTO> getAll() throws GetDataFail {
        ResponseDTO response = new ResponseDTO();
        List<ResponseDTO> responseDTO = new ArrayList<>();
        try {
            List<CTPhieuNhapDTO> nhapDTOS = ctPhieuNhapService.retrieveCTPNs();
            List list = Collections.synchronizedList(new ArrayList(nhapDTOS));

            if (responseDTO.addAll(list) == true) {
                response.setData(nhapDTOS);
            }
            response.setSuccessCode(SuccessCode.GET_ALL_CT_PHIEU_NHAP_SUCCESS);
        } catch (Exception e){
            throw new GetDataFail(""+ ErrorCode.GET_CT_PHIEU_NHAP_ERROR);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> createCTPN(@Valid @RequestBody CTPhieuNhapDTO nhapDTO) throws AddDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            CTPhieuNhapDTO dto = ctPhieuNhapService.saveCTPN(nhapDTO);
            responseDTO.setData(dto);
            responseDTO.setSuccessCode(SuccessCode.ADD_CT_PHIEU_NHAP_SUCCESS);
        } catch (Exception e){
            throw new AddDataFail(""+ErrorCode.ADD_CT_PHIEU_NHAP_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }

    ////    //update
    @PutMapping("/ctpnhap/{nhapId}")
    public ResponseEntity<ResponseDTO> updatePN(@PathVariable("nhapId") Long nhapId
                                               ) throws UpdateDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            CTPhieuNhapDTO updatePN = ctPhieuNhapService.updateCTPN(nhapId);

            responseDTO.setData(updatePN);
            responseDTO.setSuccessCode(SuccessCode.UPDATE_CT_PHIEU_NHAP_SUCCESS);
        } catch (Exception e){
            throw new UpdateDataFail(""+ErrorCode.UPDATE_CT_PHIEU_NHAP_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }

    ////    //delete
    @DeleteMapping("/ctpnhap/{nhapId}")
    public ResponseEntity<ResponseDTO> deletePN(@PathVariable("nhapId") Long nhapId) throws DeleteDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Boolean isDel = ctPhieuNhapService.deleteCTPN(nhapId);
            responseDTO.setData(isDel);
            responseDTO.setSuccessCode(SuccessCode.DELETE_CT_PHIEU_NHAP_SUCCESS);
        } catch (Exception e){
            throw new DeleteDataFail(""+ErrorCode.DELETE_CT_PHIEU_NHAP_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }
}
