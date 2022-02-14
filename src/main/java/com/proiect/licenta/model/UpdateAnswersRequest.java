package com.proiect.licenta.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateAnswersRequest {

    private long testId;

    private ScoredTest scoredTest;

    private List<PostAnswer> postAnswers;
}
