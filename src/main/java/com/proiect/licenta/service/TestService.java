package com.proiect.licenta.service;

import com.proiect.licenta.exception.ResourceNotFoundException;
import com.proiect.licenta.model.Question;
import com.proiect.licenta.model.Test;
import com.proiect.licenta.repository.CategoryRepository;
import com.proiect.licenta.repository.QuestionRepository;
import com.proiect.licenta.repository.TestRepository;
import com.proiect.licenta.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public Test save(Test test) {

        var user = userRepository.findById(userService.getUserDetails().getId());

        if (user.isEmpty()) {

            throw new ResourceNotFoundException(
                    String.format("No user with id %d was found.", userService.getUserDetails().getId()));
        }

        var category = categoryRepository.findById(test.getCategory().getId());

        if (category.isEmpty()) {

            throw new NoSuchElementException("There was no category with the given id.");
        }

        test.setCategory(category.get());
        test.setUser(user.get());

        return testRepository.save(test);
    }

    public List<Test> findAll() {

        var user = userService.getUserDetails();

        return testRepository.findAllByUserId(user.getId());
    }

    public List<Test> findAllForPlayer() {
        
        return testRepository.findAll();
    }

    public Test findById(Long id) {

        var test = testRepository.findById(id);

        return test.orElse(null);
    }

    public boolean delete(Long id) {

        var test = findById(id);

        if (test == null) {

            return false;
        }
        testRepository.delete(test);

        return true;
    }

    public Test update(Test test) {

        var actualTest = testRepository.findById(test.getId());
        var category = categoryRepository.findById(test.getCategory().getId());

        if (actualTest.isEmpty()) {
            throw new NoSuchElementException("There was no test with the given id.");
        }

        actualTest.get().setName(test.getName());
        actualTest.get().setCategory(category.get());

        return testRepository.save(actualTest.get());
    }

    public void updateTestStatus(Test test) {

        test.setWasTestTaken(true);
        testRepository.save(test);
    }

    public Test addQuestion(Question question) {

        var test = testRepository.findById(question.getTest().getId());

        if (test.isEmpty()) {

            throw new NoSuchElementException("There was no test with the given id.");
        }

        question.setTest(test.get());
        var newQuestion = questionRepository.save(question);
        test.get().getQuestions().add(newQuestion);

        return testRepository.save(test.get());
    }
}
