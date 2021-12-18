package com.proiect.licenta.converter;

import com.proiect.licenta.dto.TestDTO;
import com.proiect.licenta.model.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class TestConverter {

    @Autowired
    private ModelMapper mapper;

    public TestDTO modelToDTO(Test test) {

        var testDTO = mapper.map(test, TestDTO.class);
        testDTO.setCategoryName(test.getCategory().getTestType());

        return testDTO;
    }

    public Test dtoToModel(TestDTO testDTO) {

        return mapper.map(testDTO, Test.class);
    }
}
