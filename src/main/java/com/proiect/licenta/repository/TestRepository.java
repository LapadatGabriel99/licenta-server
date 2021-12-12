package com.proiect.licenta.repository;

import com.proiect.licenta.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    public Test getById(Long testId);

    @Query(value = "SELECT * FROM Test t WHERE t.user_id = ?1", nativeQuery = true)
    public List<Test> findAllByUserId(Long userId);
}
