package com.proiect.licenta.service;

import com.proiect.licenta.exception.ResourceNotFoundException;
import com.proiect.licenta.model.*;
import com.proiect.licenta.repository.ScoredTestRepository;
import com.proiect.licenta.repository.TestRepository;
import com.proiect.licenta.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScoredTestService {

    @Autowired
    private ScoredTestRepository scoredTestRepository;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private UserService userService;

    public ScoredTest saveAnswers(Long testId, List<PostAnswer> postAnswers) {

        var test = testRepository.findById(testId);

        if (test.isEmpty()) {

            throw new ResourceNotFoundException(String.format("Test with id: %d not found!", testId));
        }

        var scoredTest = new ScoredTest();

        calculateScore(scoredTest, postAnswers, test.get());

        scoredTest.setUser(userService.getUserDetails());
        scoredTest.setTest(test.get());
        scoredTest.setWasTestTaken(true);

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

    public ScoredTest updateAnswers(Long testId, ScoredTest scoredTest, List<PostAnswer> postAnswers) {

        var actualScoredTest = scoredTestRepository.findById(scoredTest.getId());

        if (actualScoredTest.isEmpty()) {

            throw new ResourceNotFoundException(String.format("Scored test with id: %d not found!", scoredTest.getId()));
        }

        var test = testRepository.findById(testId);

        if (test.isEmpty()) {

            throw new ResourceNotFoundException(String.format("Test with id: %d not found!", testId));
        }

        calculateScore(actualScoredTest.get(), postAnswers, test.get());
        actualScoredTest.get().setTest(test.get());
        actualScoredTest.get().setUser(userService.getUserDetails());

        return scoredTestRepository.save(actualScoredTest.get());
    }

    private void calculateScore(ScoredTest scoredTest, List<PostAnswer> postAnswers, Test test) {

        int numOfCorrectAnswers = 0;

        int numOfWrongAnswers = 0;

        var testQuestions = test.getQuestions();

        for (var testQuestion : testQuestions) {

            if (testQuestion.isHasMultipleAnswers()) {

                var questionAnswers = postAnswers
                                    .stream()
                                    .filter(x -> x.getQuestionId().equals(testQuestion.getId()));

                var questionAnswersCount = questionAnswers.count();

                var correctTestAnswers = testQuestion
                                                .getAnswers()
                                                .stream()
                                                .filter(Answer::isCorrect);

                var correctTestAnswersCount = correctTestAnswers.count();

                if (questionAnswersCount > correctTestAnswersCount) {

                    numOfWrongAnswers++;
                }
                else if (questionAnswersCount < correctTestAnswersCount) {

                    numOfWrongAnswers++;
                }
                else {

                    for (var correctAnswer : correctTestAnswers.collect(Collectors.toList())) {

                        if (questionAnswers.noneMatch(x -> x.getAnswerId() == correctAnswer.getId())) {

                            numOfWrongAnswers++;
                        }
                        else {

                            numOfCorrectAnswers++;
                        }
                    }
                }
            }
            else {

                var questionAnswers = postAnswers
                        .stream()
                        .filter(x -> x.getQuestionId().equals(testQuestion.getId()));

                var correctTestAnswers = testQuestion
                                                        .getAnswers()
                                                        .stream()
                                                        .filter(Answer::isCorrect);

                var questionAnswersCount = questionAnswers.count();

                if (questionAnswersCount > 1) {

                    numOfWrongAnswers++;
                }
                else if (questionAnswersCount < 1) {

                    numOfWrongAnswers++;
                }
                else {

                    for (var correctAnswer : correctTestAnswers.collect(Collectors.toList())) {

                        if (questionAnswers.anyMatch(x -> x.getAnswerId() == correctAnswer.getId())) {

                            numOfCorrectAnswers++;
                            break;
                        }
                    }
                }
            }
        }

        scoredTest.setNumOfWrongAnswers(numOfWrongAnswers);
        scoredTest.setNumOfCorrectAnswers(numOfCorrectAnswers);
    }
}
