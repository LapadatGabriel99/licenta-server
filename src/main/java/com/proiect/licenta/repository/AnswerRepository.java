package com.proiect.licenta.repository;

import com.proiect.licenta.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    public Answer getById(Long answerId);
}
