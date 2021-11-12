package com.example.apiweblaptop.controller;

import com.example.apiweblaptop.dto.CategoryDTO;
import com.example.apiweblaptop.dto.ErrorCode;
import com.example.apiweblaptop.dto.ResponseDTO;
import com.example.apiweblaptop.dto.SuccessCode;
import com.example.apiweblaptop.exception.*;
import com.example.apiweblaptop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<ResponseDTO> getAllCategory() throws GetDataFail {
        ResponseDTO response = new ResponseDTO();
        List<ResponseDTO> responseDTO = new ArrayList<>();

        try {
            List<CategoryDTO> categoryDTOS = categoryService.retrieveCategories();
            List list = Collections.synchronizedList(new ArrayList(categoryDTOS));

            //response.setData(responseDTO.addAll(list));
            if (responseDTO.addAll(list) == true) {
                response.setData(categoryDTOS);
            }
            response.setSuccessCode(SuccessCode.GET_ALL_CATEGORY_SUCCESS);
        } catch (Exception e){
            throw new GetDataFail(""+ ErrorCode.GET_CATEGORY_ERROR);
        }
        return ResponseEntity.ok(response);

    }

    @GetMapping("/{category_id}")
    public ResponseEntity<ResponseDTO> getCate(@PathVariable("category_id") Long id) throws ResourceNotFoundException {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Optional<CategoryDTO> categoryDTO = categoryService.getCate(id);

            responseDTO.setData(categoryDTO);
            responseDTO.setSuccessCode(SuccessCode.FIND_CATEGORY_SUCCESS);
        } catch (Exception e) {
            throw new ResourceNotFoundException(""+ErrorCode.FIND_CATEGORY_ERROR);
        }
        return ResponseEntity.ok(responseDTO);
    }

    //insert

        @PostMapping("/add")
        public ResponseEntity<ResponseDTO> createCate( @RequestBody CategoryDTO categoryDTO) throws AddDataFail {
            ResponseDTO responseDTO = new ResponseDTO();
            try {
                CategoryDTO dto = categoryService.saveCategory(categoryDTO);
                responseDTO.setData(dto);
                responseDTO.setSuccessCode(SuccessCode.ADD_CATEGORY_SUCCESS);
            } catch (Exception e){
                throw new AddDataFail(""+ErrorCode.ADD_CATEGORY_ERROR);
            }

            return ResponseEntity.ok(responseDTO);
        }

    @PutMapping("/{category_id}")
    public ResponseEntity<ResponseDTO> updateCate(@PathVariable(value = "category_id") Long categoryId, @RequestBody CategoryDTO categoryDTO) throws UpdateDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            CategoryDTO updateCate = categoryService.updateCategory(categoryId, categoryDTO);

            responseDTO.setData(updateCate);
            responseDTO.setSuccessCode(SuccessCode.UPDATE_CATEGORY_SUCCESS);
        } catch (Exception e){
            throw new UpdateDataFail(""+ErrorCode.UPDATE_CATEGORY_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }

    //    //delete
    @DeleteMapping("/{category_id}")
    public ResponseEntity<ResponseDTO> deleteCate(@PathVariable(value = "category_id") Long categoryId) throws DeleteDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        System.out.println(categoryId);
        try {
            Boolean isDel = categoryService.deleteCategory(categoryId);
            responseDTO.setData(isDel);
            responseDTO.setSuccessCode(SuccessCode.DELETE_CATEGORY_SUCCESS);
        } catch (Exception e){
            throw new DeleteDataFail(""+ErrorCode.DELETE_CATEGORY_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }
}
