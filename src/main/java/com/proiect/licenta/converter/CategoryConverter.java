package com.proiect.licenta.converter;

import com.proiect.licenta.dto.CategoryDTO;
import com.proiect.licenta.model.Category;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryConverter {

    @Autowired
    private ModelMapper mapper;

    public CategoryDTO modelToDTO(Category category) {

        return mapper.map(category, CategoryDTO.class);
    }

    public Category dtoToModel(CategoryDTO categoryDTO) {

        return mapper.map(categoryDTO, Category.class);
    }
}
