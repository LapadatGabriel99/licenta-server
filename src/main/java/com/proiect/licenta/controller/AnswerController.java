package com.proiect.licenta.controller;

import com.proiect.licenta.converter.AnswerConverter;
import com.proiect.licenta.dto.AnswerDTO;
import com.proiect.licenta.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/answer")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private AnswerConverter answerConverter;

    @PostMapping("/create/{questionId}")
    public ResponseEntity<AnswerDTO> createAnswer(@PathVariable(value = "questionId") Long questionId,
                                                  @Valid @RequestBody AnswerDTO answerDTO) {
        return new ResponseEntity<>(
                answerConverter.modelToDTO(answerService.createAnswer(questionId, answerConverter.dtoToModel(answerDTO))),
                HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnswerDTO> getAnswer(@PathVariable(value = "id") Long id) {

        return new ResponseEntity<>(answerConverter.modelToDTO((answerService.getById(id))), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AnswerDTO>> getAllAnswers() {

        return new ResponseEntity<>(
                answerService.findAll()
                        .stream()
                        .map(answer -> answerConverter.modelToDTO(answer))
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AnswerDTO> updateAnswer(@PathVariable(value = "id") Long id,
                                          @Valid @RequestBody AnswerDTO answerDTO) {

        return new ResponseEntity<>(
                answerConverter.modelToDTO(answerService.updateAnswer(id, answerConverter.dtoToModel(answerDTO))),
                HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteAnswer(@PathVariable(value = "id") Long id) {

        if (!answerService.deleteAnswer(id)) {

            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
