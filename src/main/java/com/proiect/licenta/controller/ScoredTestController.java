package com.proiect.licenta.controller;

import com.proiect.licenta.converter.ScoredTestConverter;
import com.proiect.licenta.dto.ScoredTestDTO;
import com.proiect.licenta.service.ScoredTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/scored-test")
public class ScoredTestController {

    @Autowired
    private ScoredTestService scoredTestService;

    @Autowired
    private ScoredTestConverter scoredTestConverter;

    @PostMapping("/post-answers")
    public ResponseEntity<ScoredTestDTO> postAnswers(@Valid @RequestBody ScoredTestDTO scoredTestDTO) {

        return new ResponseEntity<>(
                scoredTestConverter.modelToDTO(scoredTestService.saveAnswers(
                        scoredTestDTO.getTestId(),
                        scoredTestConverter.dtoToModel(scoredTestDTO))), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ScoredTestDTO>> getAll() {

        return new ResponseEntity<>(
                scoredTestService.findAll()
                            .stream()
                            .map(scoredTest -> scoredTestConverter.modelToDTO(scoredTest))
                            .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScoredTestDTO> getScoredTestById(@PathVariable(name = "id") Long id) {

        return new ResponseEntity<>(
                scoredTestConverter.modelToDTO(scoredTestService.findById(id)), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ScoredTestDTO> updateAnswers(@Valid @RequestBody ScoredTestDTO scoredTestDTO) {

        return new ResponseEntity<>(
                scoredTestConverter.modelToDTO(scoredTestService.updateAnswers(
                        scoredTestDTO.getTestId(),
                        scoredTestConverter.dtoToModel(scoredTestDTO))), HttpStatus.ACCEPTED);
    }
}
