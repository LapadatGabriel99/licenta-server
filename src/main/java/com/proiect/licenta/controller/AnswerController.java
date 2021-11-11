package com.proiect.licenta.controller;

import com.proiect.licenta.converter.AnswerConverter;
import com.proiect.licenta.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/answer")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private AnswerConverter answerConverter;
}
