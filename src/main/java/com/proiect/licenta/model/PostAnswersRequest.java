package com.proiect.licenta.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostAnswersRequest {

    private Long testId;

    private List<PostAnswer> postAnswers;
}
