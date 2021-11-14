package com.proiect.licenta.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
public class TestDTO {

    private Long id;

    @NotBlank(message = "Name can't be empty!")
    private String name;

    private Long categoryId;

    private String categoryName;

    private List<QuestionDTO> questionDTOS;
}
