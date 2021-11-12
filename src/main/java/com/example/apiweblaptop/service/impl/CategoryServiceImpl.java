package com.example.apiweblaptop.service.impl;

import com.example.apiweblaptop.dto.CategoryDTO;
import com.example.apiweblaptop.dto.ErrorCode;
import com.example.apiweblaptop.entity.Category;
import com.example.apiweblaptop.exception.ResourceNotFoundException;
import com.example.apiweblaptop.repo.CategoryRepository;
import com.example.apiweblaptop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryDTO> retrieveCategories() {
        List<Category> categories = categoryRepository.findAll();
        return new CategoryDTO().entityToDTO(categories);
    }

    @Override
    public Optional<CategoryDTO> getCate(Long cateId) throws ResourceNotFoundException {
        Category category = categoryRepository.findById(cateId).orElseThrow(() -> new ResourceNotFoundException(""+ ErrorCode.FIND_CATEGORY_ERROR));
        return Optional.of(new CategoryDTO().entityToDTO(category));
    }

    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) throws ResourceNotFoundException {
        Category category = new CategoryDTO().dtoToEntity(categoryDTO);
        return new CategoryDTO().entityToDTO(categoryRepository.save(category));
    }

    @Override
    public Boolean deleteCategory(Long categoryId) throws ResourceNotFoundException {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException(""+ ErrorCode.FIND_CATEGORY_ERROR));
        this.categoryRepository.delete(category);
        return true;
    }

    @Override
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) throws ResourceNotFoundException {
        Category cateExist = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException(""+ ErrorCode.FIND_CATEGORY_ERROR));
        cateExist.setCategoryName(categoryDTO.getCategory_name());

        Category category = new Category();
        category = categoryRepository.save(cateExist);
        return new CategoryDTO().entityToDTO(category);
    }
}
