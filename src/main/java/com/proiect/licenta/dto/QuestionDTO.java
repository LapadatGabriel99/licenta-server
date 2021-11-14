package com.proiect.licenta.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuestionDTO {

    private Long questionId;

    private String questionText;

    private boolean hasMultipleAnswers;

    private List<AnswerDTO> answerDTOS;
}
