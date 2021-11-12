package com.example.apiweblaptop.controller;

import com.example.apiweblaptop.dto.BrandDTO;
import com.example.apiweblaptop.dto.ErrorCode;
import com.example.apiweblaptop.dto.ResponseDTO;
import com.example.apiweblaptop.dto.SuccessCode;
import com.example.apiweblaptop.exception.*;
import com.example.apiweblaptop.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping("")
    public ResponseEntity<ResponseDTO> getAllBrand() throws GetDataFail {
        ResponseDTO response = new ResponseDTO();
        List<ResponseDTO> responseDTO = new ArrayList<>();

        try {
            List<BrandDTO> brandDTOS = brandService.retrieveBrands();
            List list = Collections.synchronizedList(new ArrayList(brandDTOS));

            if (responseDTO.addAll(list) == true) {
                response.setData(brandDTOS);
            }
            response.setSuccessCode(SuccessCode.GET_ALL_BRAND_SUCCESS);
        } catch (Exception e){
            throw new GetDataFail(""+ ErrorCode.GET_BRAND_ERROR);
        }
        return ResponseEntity.ok(response);

    }

    @GetMapping("/{brand_id}")
    public ResponseEntity<ResponseDTO> getBrand(@PathVariable("brand_id") Long id) throws ResourceNotFoundException {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Optional<BrandDTO> brandDTO = brandService.getBrand(id);

            responseDTO.setData(brandDTO);
            responseDTO.setSuccessCode(SuccessCode.FIND_BRAND_SUCCESS);
        } catch (Exception e) {
            throw new ResourceNotFoundException(""+ErrorCode.FIND_BRAND_ERROR);
        }
        return ResponseEntity.ok(responseDTO);
    }

    //insert

    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> createBrand(@RequestBody BrandDTO brandDTO) throws AddDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        System.out.println(brandDTO);
        try {
            BrandDTO dto = brandService.saveBrand(brandDTO);
            responseDTO.setData(dto);
            responseDTO.setSuccessCode(SuccessCode.ADD_BRAND_SUCCESS);

        } catch (Exception e){
            throw new AddDataFail(""+ErrorCode.ADD_BRAND_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }

    //update
    @PutMapping("/{brand_id}")
    public ResponseEntity<ResponseDTO> updateBrand(@PathVariable(value = "brand_id") Long brandId, @RequestBody BrandDTO brandDTO) throws UpdateDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            BrandDTO updateBrand = brandService.updateBrand(brandId, brandDTO);

            responseDTO.setData(updateBrand);
            responseDTO.setSuccessCode(SuccessCode.UPDATE_BRAND_SUCCESS);
        } catch (Exception e){
            throw new UpdateDataFail(""+ErrorCode.UPDATE_BRAND_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }

    //    //delete
    @DeleteMapping("/brand/{brand_id}")
    public ResponseEntity<ResponseDTO> deleteBrand(@PathVariable(value = "brand_id") Long brandId) throws DeleteDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Boolean isDel = brandService.deleteBrand(brandId);
            responseDTO.setData(isDel);
            responseDTO.setSuccessCode(SuccessCode.DELETE_BRAND_SUCCESS);
        } catch (Exception e){
            throw new DeleteDataFail(""+ErrorCode.DELETE_BRAND_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }
}
