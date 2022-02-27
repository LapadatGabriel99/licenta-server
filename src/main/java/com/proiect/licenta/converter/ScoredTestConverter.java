package com.proiect.licenta.converter;

import com.proiect.licenta.dto.ScoredTestDTO;
import com.proiect.licenta.model.ScoredTest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class ScoredTestConverter {

    @Autowired
    private ModelMapper mapper;

    public ScoredTestDTO modelToDTO(ScoredTest scoredTest) {

        var dto = mapper.map(scoredTest, ScoredTestDTO.class);
        dto.setTestId(scoredTest.getTest().getId());
        dto.setCategoryName(scoredTest.getTest().getCategoryName());

        return dto;
    }

    public ScoredTest dtoToModel(ScoredTestDTO scoredTestDTO) {

        return mapper.map(scoredTestDTO, ScoredTest.class);
    }
}
