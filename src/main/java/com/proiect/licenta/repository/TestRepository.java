package com.proiect.licenta.repository;

import com.proiect.licenta.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    public Test getById(Long testId);
}
