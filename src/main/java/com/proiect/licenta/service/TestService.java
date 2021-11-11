package com.proiect.licenta.service;

import com.proiect.licenta.model.Question;
import com.proiect.licenta.model.Test;
import com.proiect.licenta.repository.CategoryRepository;
import com.proiect.licenta.repository.QuestionRepository;
import com.proiect.licenta.repository.TestRepository;
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

    public Test save(Test test) {

        var category = categoryRepository.findById(test.getCategory().getId());

        if (category.isEmpty()) {

            throw new NoSuchElementException("There was no category with the given id.");
        }

        test.setCategory(category.get());

        return testRepository.save(test);
    }

    public List<Test> findAll() {
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
