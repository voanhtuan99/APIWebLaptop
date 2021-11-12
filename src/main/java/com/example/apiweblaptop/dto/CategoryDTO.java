package com.example.apiweblaptop.dto;

import com.example.apiweblaptop.entity.Category;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CategoryDTO {
    private Long id;
    private String category_name;

    public CategoryDTO entityToDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setCategory_name(category.getCategoryName());
        return  categoryDTO;
    }

    public Category dtoToEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setCategoryName(categoryDTO.getCategory_name());
        return category;
    }

    public List<CategoryDTO> entityToDTO(List<Category> categories) {
        return categories.stream().map(x-> entityToDTO(x)).collect(Collectors.toList());
    }

    public List<Category> dtoToEntity(List<CategoryDTO> categoryDTOS) {
        return categoryDTOS.stream().map(x-> dtoToEntity(x)).collect(Collectors.toList());
    }
}
