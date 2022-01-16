package com.proiect.licenta.converter;

import com.proiect.licenta.dto.ScoredTestDTO;
import com.proiect.licenta.model.ScoredTest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class ScoredTestConverter {

    @Autowired
    private ModelMapper mapper;

    public ScoredTestDTO modelToDTO(ScoredTest scoredTest) {

        return mapper.map(scoredTest, ScoredTestDTO.class);
    }

    public ScoredTest dtoToModel(ScoredTestDTO scoredTestDTO) {

        return mapper.map(scoredTestDTO, ScoredTest.class);
    }
}
