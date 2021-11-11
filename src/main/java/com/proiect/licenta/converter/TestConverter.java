package com.proiect.licenta.converter;

import com.proiect.licenta.dto.TestDTO;
import com.proiect.licenta.model.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class TestConverter {

    @Autowired
    private ModelMapper mapper;

    public TestDTO modelToDTO(Test test) {

        return mapper.map(test, TestDTO.class);
    }

    public Test dtoToModel(TestDTO testDTO) {

        return mapper.map(testDTO, Test.class);
    }
}
