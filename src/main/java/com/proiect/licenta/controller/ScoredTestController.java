package com.proiect.licenta.controller;

import com.proiect.licenta.converter.ScoredTestConverter;
import com.proiect.licenta.dto.ScoredTestDTO;
import com.proiect.licenta.model.PostAnswersRequest;
import com.proiect.licenta.model.UpdateAnswersRequest;
import com.proiect.licenta.service.ScoredTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('PLAYER')")
    public ResponseEntity<ScoredTestDTO> postAnswers(@Valid @RequestBody PostAnswersRequest postAnswersRequest) {

        return new ResponseEntity<>(
                scoredTestConverter.modelToDTO(scoredTestService.saveAnswers(
                        postAnswersRequest.getTestId(),
                        postAnswersRequest.getPostAnswers())), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('PLAYER')")
    public ResponseEntity<List<ScoredTestDTO>> getAll() {

        return new ResponseEntity<>(
                scoredTestService.findAll()
                            .stream()
                            .map(scoredTest -> scoredTestConverter.modelToDTO(scoredTest))
                            .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PLAYER')")
    public ResponseEntity<ScoredTestDTO> getScoredTestById(@PathVariable(name = "id") Long id) {

        return new ResponseEntity<>(
                scoredTestConverter.modelToDTO(scoredTestService.findById(id)), HttpStatus.OK);
    }

    @GetMapping("/get-by-test-id/{id}")
    @PreAuthorize("hasAuthority('PLAYER')")
    public ResponseEntity<ScoredTestDTO> getScoredTestByTestId(@PathVariable(name = "id") Long id) {

        return new ResponseEntity<>(
                scoredTestConverter.modelToDTO(scoredTestService.findByTestId(id)), HttpStatus.OK);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('PLAYER')")
    public ResponseEntity<ScoredTestDTO> updateAnswers(@Valid @RequestBody UpdateAnswersRequest request) {

        return new ResponseEntity<>(
                scoredTestConverter.modelToDTO(scoredTestService.updateAnswers(
                        request.getTestId(),
                        request.getScoredTest(),
                        request.getPostAnswers())), HttpStatus.ACCEPTED);
    }
}
