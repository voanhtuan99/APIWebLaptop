package com.example.apiweblaptop.controller;

import com.example.apiweblaptop.dto.*;
import com.example.apiweblaptop.entity.Company;
import com.example.apiweblaptop.exception.*;
import com.example.apiweblaptop.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/company")
public class CompanyController {
    @Autowired
    CompanyService companyService;

    @GetMapping("")
    public ResponseEntity<ResponseDTO> getCompany() throws GetDataFail{
        ResponseDTO response = new ResponseDTO();
        List<ResponseDTO> responseDTO = new ArrayList<>();

        try {
            List<CompanyDTO> companyDTOS = companyService.retrieveCompany();
            List list = Collections.synchronizedList(new ArrayList(companyDTOS));

            //response.setData(responseDTO.addAll(list));
            if (responseDTO.addAll(list) == true) {
                response.setData(companyDTOS);
            }
            response.setSuccessCode(SuccessCode.GET_ALL_CATEGORY_SUCCESS);
        } catch (Exception e){
            throw new GetDataFail(""+ ErrorCode.GET_CATEGORY_ERROR);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{company_id}")
    public ResponseEntity<ResponseDTO> getCate(@PathVariable("company_id") Long id) throws ResourceNotFoundException {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Optional<CompanyDTO> companyDTO = companyService.getCompany(id);

            responseDTO.setData(companyDTO);
            responseDTO.setSuccessCode(SuccessCode.FIND_CATEGORY_SUCCESS);
        } catch (Exception e) {
            throw new ResourceNotFoundException(""+ErrorCode.FIND_CATEGORY_ERROR);
        }
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> createCate( @RequestBody CompanyDTO companyDTO) throws AddDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            CompanyDTO dto = companyService.saveCompany(companyDTO);
            responseDTO.setData(dto);
            responseDTO.setSuccessCode(SuccessCode.ADD_CATEGORY_SUCCESS);
        } catch (Exception e){
            throw new AddDataFail(""+ErrorCode.ADD_CATEGORY_ERROR);
        }
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{company_id}")
    public ResponseEntity<ResponseDTO> updateCate(@PathVariable(value = "company_id") Long companyId, @RequestBody CompanyDTO companyDTO) throws UpdateDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            CompanyDTO updateCompany = companyService.updateCompany(companyId, companyDTO);

            responseDTO.setData(updateCompany);
            responseDTO.setSuccessCode(SuccessCode.UPDATE_CATEGORY_SUCCESS);
        } catch (Exception e){
            throw new UpdateDataFail(""+ErrorCode.UPDATE_CATEGORY_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }

    //    //delete
    @DeleteMapping("/{company_id}")
    public ResponseEntity<ResponseDTO> deleteCate(@PathVariable(value = "company_id") Long companyId) throws DeleteDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Boolean isDel = companyService.deleteCompany(companyId);
            responseDTO.setData(isDel);
            responseDTO.setSuccessCode(SuccessCode.DELETE_CATEGORY_SUCCESS);
        } catch (Exception e){
            throw new DeleteDataFail(""+ErrorCode.DELETE_CATEGORY_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }
}
