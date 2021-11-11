package com.proiect.licenta.converter;

import com.proiect.licenta.dto.AnswerDTO;
import com.proiect.licenta.model.Answer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class AnswerConverter {

    @Autowired
    private ModelMapper mapper;

    public AnswerDTO modelToDTO(Answer answer) {

        return mapper.map(answer, AnswerDTO.class);
    }

    public Answer dtoToModel(AnswerDTO answerDTO) {

        return mapper.map(answerDTO, Answer.class);
    }
}
