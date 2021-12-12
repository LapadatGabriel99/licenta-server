package com.proiect.licenta.repository;

import com.proiect.licenta.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    public Category getById(Long categoryId);

    @Query(value = "SELECT * FROM Category c WHERE c.user_id = ?1", nativeQuery = true)
    public List<Category> findAllByUserId(Long userId);
}
