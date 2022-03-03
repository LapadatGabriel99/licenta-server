package com.proiect.licenta.controller;

import com.proiect.licenta.converter.TestConverter;
import com.proiect.licenta.dto.TestDTO;
import com.proiect.licenta.exception.ResourceNotFoundException;
import com.proiect.licenta.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private TestService testService;

    @Autowired
    private TestConverter testConverter;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<TestDTO> createTest(@Valid @RequestBody TestDTO testDTO) {

        return new ResponseEntity<>(
                testConverter.modelToDTO(testService.save(testConverter.dtoToModel(testDTO))), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<TestDTO>> getAllTests() {

        return new ResponseEntity<>(
                testService.findAll()
                        .stream()
                        .map(test -> testConverter.modelToDTO(test))
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/player/all")
    @PreAuthorize("hasAuthority('PLAYER')")
    public ResponseEntity<List<TestDTO>> getAllTestsForPlayer() {

        return new ResponseEntity<>(
                testService.findAllForPlayer()
                    .stream()
                    .map(test -> testConverter.modelToDTO(test))
                    .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<TestDTO> getTestById(@PathVariable(name = "id") Long id) {

        var test = testService.findById(id);

        if (test == null) {

            throw new ResourceNotFoundException(String.format("No such test with id: %d", id));
        }

        return new ResponseEntity<>(testConverter.modelToDTO(test), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Boolean> deleteTest(@PathVariable(name = "id") Long id) {

        if (!testService.delete(id)) {

            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<TestDTO> updateTest(@Valid @RequestBody TestDTO testDTO) {

        return new ResponseEntity<>(
                testConverter.modelToDTO(testService.update(testConverter.dtoToModel(testDTO))),
                HttpStatus.ACCEPTED
        );
    }
}
