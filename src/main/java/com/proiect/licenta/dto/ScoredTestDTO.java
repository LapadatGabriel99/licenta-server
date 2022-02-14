package com.proiect.licenta.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ScoredTestDTO {

    private Long id;

    private Long testId;

    @NotBlank(message = "Name can't be empty!")
    private String name;

    private String categoryName;

    private boolean wasTestModified;

    private int numOfCorrectAnswers;

    private int numOfWrongAnswers;

    private boolean wasTestTaken;
}
