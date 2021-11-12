package com.example.apiweblaptop.service;




import com.example.apiweblaptop.dto.CategoryDTO;
import com.example.apiweblaptop.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;


public interface CategoryService {
    public List<CategoryDTO> retrieveCategories();

    public Optional<CategoryDTO> getCate(Long categoryId) throws ResourceNotFoundException;

    public CategoryDTO saveCategory(CategoryDTO categoryDTO) throws ResourceNotFoundException;

    public Boolean deleteCategory(Long categoryId) throws ResourceNotFoundException;

    public CategoryDTO updateCategory(Long categoryId,CategoryDTO categoryDTO) throws ResourceNotFoundException;


}
