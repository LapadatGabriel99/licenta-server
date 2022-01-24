package com.proiect.licenta.service;

import com.proiect.licenta.exception.ResourceNotFoundException;
import com.proiect.licenta.model.ScoredTest;
import com.proiect.licenta.repository.ScoredTestRepository;
import com.proiect.licenta.repository.TestRepository;
import com.proiect.licenta.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoredTestService {

    @Autowired
    private ScoredTestRepository scoredTestRepository;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private UserService userService;

    public ScoredTest saveAnswers(Long testId, ScoredTest scoredTest) {

        var test = testRepository.findById(testId);

        if (test.isEmpty()) {

            throw new ResourceNotFoundException(String.format("Test with id: %d not found!", testId));
        }

        scoredTest.setUser(userService.getUserDetails());
        scoredTest.setTest(test.get());

        return scoredTestRepository.save(scoredTest);
    }

    public List<ScoredTest> findAll() {

        return scoredTestRepository.findAllByUserId(userService.getUserDetails().getId());
    }

    public ScoredTest findById(Long id) {

        var scoredTest = scoredTestRepository.findById(id);

        if (scoredTest.isEmpty()) {

            throw new ResourceNotFoundException(String.format("Scored test with id: %d not found!", id));
        }

        return scoredTest.get();
    }

    public ScoredTest updateAnswers(Long testId, ScoredTest scoredTest) {

        var actualScoredTest = scoredTestRepository.findById(scoredTest.getId());

        if (actualScoredTest.isEmpty()) {

            throw new ResourceNotFoundException(String.format("Scored test with id: %d not found!", scoredTest.getId()));
        }

        var test = testRepository.findById(testId);

        if (test.isEmpty()) {

            throw new ResourceNotFoundException(String.format("Test with id: %d not found!", testId));
        }

        actualScoredTest.get().setTest(test.get());
        actualScoredTest.get().setUser(userService.getUserDetails());
        actualScoredTest.get().setNumOfCorrectAnswers(scoredTest.getNumOfCorrectAnswers());
        actualScoredTest.get().setNumOfWrongAnswers(scoredTest.getNumOfWrongAnswers());

        return scoredTestRepository.save(actualScoredTest.get());
    }
}
