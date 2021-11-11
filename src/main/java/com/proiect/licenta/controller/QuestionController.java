package com.proiect.licenta.controller;

import com.proiect.licenta.converter.QuestionConverter;
import com.proiect.licenta.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionConverter questionConverter;
}
