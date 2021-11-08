package com.proiect.licenta.repository;

import com.proiect.licenta.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    public Category getById(Long categoryId);
}
