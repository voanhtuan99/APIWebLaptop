package com.example.apiweblaptop.controller;

import com.example.apiweblaptop.dto.ErrorCode;
import com.example.apiweblaptop.dto.ImageDTO;
import com.example.apiweblaptop.dto.ResponseDTO;
import com.example.apiweblaptop.dto.SuccessCode;
import com.example.apiweblaptop.exception.*;
import com.example.apiweblaptop.service.ImageProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/images")
public class ImageProductController {
    @Autowired
    private ImageProductService imageService;

    @GetMapping("")
    public ResponseEntity<ResponseDTO> getAllImage() throws GetDataFail {
        ResponseDTO response = new ResponseDTO();
        List<ResponseDTO> responseDTO = new ArrayList<>();
        try {
            List<ImageDTO> images = imageService.retrieveProductImages();
            List list = Collections.synchronizedList(new ArrayList(images));

            if (responseDTO.addAll(list) == true) {
                response.setData(images);
            }
            response.setSuccessCode(SuccessCode.GET_ALL_PRODUCT_IMAGE_SUCCESS);
        } catch (Exception e){
            throw new GetDataFail(""+ ErrorCode.GET_PRODUCT_IMAGE_ERROR);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{image_id}")
    public ResponseEntity<ResponseDTO> findProductImage(@PathVariable("image_id") Long imageId) throws ResourceNotFoundException {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Optional<ImageDTO> imageDTO = imageService.getProductImage(imageId);

            responseDTO.setData(imageDTO);
            responseDTO.setSuccessCode(SuccessCode.FIND_PRODUCT_IMAGE_SUCCESS);
        } catch (Exception e){
            throw new ResourceNotFoundException(""+ErrorCode.FIND_PRODUCT_IMAGE_ERROR);
        }
        return ResponseEntity.ok(responseDTO);
    }

    // Get by id product
    @GetMapping("/product/{product_id}")
    public ResponseEntity<ResponseDTO> findImageByProductID(@PathVariable("product_id") Long productId) throws ResourceNotFoundException {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Optional<ImageDTO> imageDTO = imageService.getProductImage(productId);

            responseDTO.setData(imageDTO);
            responseDTO.setSuccessCode(SuccessCode.FIND_PRODUCT_IMAGE_SUCCESS);
        } catch (Exception e){
            throw new ResourceNotFoundException(""+ErrorCode.FIND_PRODUCT_IMAGE_ERROR);
        }
        return ResponseEntity.ok(responseDTO);
    }

    //insert
    @PostMapping("/image")
    public ResponseEntity<ResponseDTO>  createProductImage(@RequestBody ImageDTO imageDTO) throws AddDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            ImageDTO dto = imageService.saveProductImage(imageDTO);
            responseDTO.setData(dto);
            responseDTO.setSuccessCode(SuccessCode.ADD_PRODUCT_IMAGE_SUCCESS);
        } catch (Exception e){
            throw new AddDataFail(""+ErrorCode.ADD_PRODUCT_IMAGE_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }

    //    //update
    @PutMapping("/image/{image_id}")
    public ResponseEntity<ResponseDTO> updateProductImage(@PathVariable(value = "image_id") Long imageId,
                                                          @RequestBody ImageDTO imageDTO) throws UpdateDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            ImageDTO updateImage = imageService.updateProductImage(imageId, imageDTO);

            responseDTO.setData(updateImage);
            responseDTO.setSuccessCode(SuccessCode.UPDATE_PRODUCT_IMAGE_SUCCESS);
        } catch (Exception e){
            throw new UpdateDataFail(""+ErrorCode.UPDATE_PRODUCT_IMAGE_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }

    //    //delete
    @DeleteMapping("/image/{image_id}")
    public ResponseEntity<ResponseDTO> deleteProductImage(@PathVariable(value = "image_id") Long imageId) throws DeleteDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Boolean isDel = imageService.deleteProductImage(imageId);
            responseDTO.setData(isDel);
            responseDTO.setSuccessCode(SuccessCode.DELETE_PRODUCT_IMAGE_SUCCESS);
        } catch (Exception e){
            throw new DeleteDataFail(""+ErrorCode.DELETE_PRODUCT_IMAGE_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }
}
