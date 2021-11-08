package com.proiect.licenta.service;

import com.proiect.licenta.exception.ResourceNotFoundException;
import com.proiect.licenta.model.Question;
import com.proiect.licenta.repository.QuestionRepository;
import com.proiect.licenta.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TestRepository testRepository;

    public Question createQuestion(Long testId, Question question) {

        question.setTest(testRepository.getById(testId));

        return questionRepository.save(question);
    }

    public Question getById(Long questionId) {

        Question question = questionRepository.getById(questionId);

        if (question == null) {

            throw new ResourceNotFoundException(String.format("Question with id: %d not found!", questionId));
        }

        return question;
    }

    public List<Question> findAll() {

        return questionRepository.findAll();
    }

    public Question updateQuestion(Long questionId, Question question) {

        var actualQuestion = questionRepository.getById(questionId);

        actualQuestion.setQuestionText(question.getQuestionText());
        actualQuestion.setHasMultipleAnswers(question.isHasMultipleAnswers());

        return questionRepository.save(actualQuestion);
    }

    public boolean deleteQuestion(Long questionId) {

        var question = questionRepository.findById(questionId);

        if (question.isEmpty()) {
            return false;
        }

        questionRepository.deleteById(questionId);

        return true;
    }
}
