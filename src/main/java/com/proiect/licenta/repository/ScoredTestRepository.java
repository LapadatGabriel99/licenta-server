package com.proiect.licenta.repository;

import com.proiect.licenta.model.ScoredTest;
import com.proiect.licenta.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoredTestRepository extends JpaRepository<ScoredTest, Long> {

    public ScoredTest getById(Long testId);

    @Query(value = "SELECT * FROM ScoredTest st WHERE st.user_id = ?1", nativeQuery = true)
    public List<ScoredTest> findAllByUserId(Long userId);
}
