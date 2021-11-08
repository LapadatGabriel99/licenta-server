package com.proiect.licenta.service;

import com.proiect.licenta.exception.ResourceNotFoundException;
import com.proiect.licenta.model.Answer;
import com.proiect.licenta.repository.AnswerRepository;
import com.proiect.licenta.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public Answer createAnswer(Long questionId, Answer answer) {

        answer.setQuestion(questionRepository.getById(questionId));

        return answerRepository.save(answer);
    }

    public Answer getById(Long answerId) {

        Answer answer = answerRepository.getById(answerId);

        if (answer == null) {

            throw new ResourceNotFoundException(String.format("Answer with id: %d not found!", answerId));
        }

        return answer;
    }

    public List<Answer> findAll() {

        return answerRepository.findAll();
    }

    public Answer updateAnswer(Long answerId, Answer answer) {

        var actualAnswer = answerRepository.getById(answerId);

        actualAnswer.setAnswerText(answer.getAnswerText());
        actualAnswer.setCorrect(answer.isCorrect());

        return answerRepository.save(actualAnswer);
    }

    public boolean deleteAnswer(Long answerId) {

        var answer = questionRepository.findById(answerId);

        if (answer.isEmpty()) {
            return false;
        }

        questionRepository.deleteById(answerId);

        return true;
    }
}
