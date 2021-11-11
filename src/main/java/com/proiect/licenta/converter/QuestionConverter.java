package com.proiect.licenta.converter;

import com.proiect.licenta.dto.QuestionDTO;
import com.proiect.licenta.model.Question;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class QuestionConverter {

    @Autowired
    private ModelMapper mapper;

    public QuestionDTO modelToDTO(Question question) {

        return mapper.map(question, QuestionDTO.class);
    }

    public Question dtoToModel(QuestionDTO questionDTO) {

        return mapper.map(questionDTO, Question.class);
    }
}
