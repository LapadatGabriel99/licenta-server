package com.proiect.licenta.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerDTO {

    private Long id;

    private String answerText;

    private boolean isCorrect;
}
