package com.proiect.licenta.controller;

import com.proiect.licenta.converter.QuestionConverter;
import com.proiect.licenta.dto.QuestionDTO;
import com.proiect.licenta.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionConverter questionConverter;

    @PostMapping("/create/{testId}")
    public ResponseEntity<QuestionDTO> createQuestion(@PathVariable(value = "testId") Long testId,
                                            @Valid @RequestBody QuestionDTO questionDTO) {

        return new ResponseEntity<>(
                questionConverter.modelToDTO(questionService.createQuestion(testId, questionConverter.dtoToModel(questionDTO))),
                HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDTO> getQuestion(@PathVariable(value = "id") Long id) {

        return new ResponseEntity<>(questionConverter.modelToDTO(questionService.getById(id)), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<QuestionDTO>> getAllQuestions() {

        return new ResponseEntity<>(
                questionService.findAll()
                        .stream()
                        .map(question -> questionConverter.modelToDTO(question))
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<QuestionDTO> updateQuestion(@PathVariable(value = "id") Long id,
                                            @Valid @RequestBody QuestionDTO questionDTO) {

         return new ResponseEntity<>(
                 questionConverter.modelToDTO(questionService.updateQuestion(id, questionConverter.dtoToModel(questionDTO))),
                 HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteQuestion(@PathVariable(value = "id") Long id) {

        if (!questionService.deleteQuestion(id)) {

            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
