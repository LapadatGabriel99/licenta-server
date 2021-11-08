package com.proiect.licenta.service;

import com.proiect.licenta.model.Category;
import com.proiect.licenta.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category save(Category category) {

        if (category.getId() != null) {

            throw new RuntimeException("An Id cannot be assigned for creation");
        }

        return categoryRepository.save(category);
    }

    public Category update(Category category) {

        var actualCategory = categoryRepository.getById(category.getId());
        actualCategory.setTestType(category.getTestType());

        return categoryRepository.save(actualCategory);
    }

    public Optional<Category> findById(Long id) {

        return categoryRepository.findById(id);
    }

    public boolean deleteById(Long id) {

        var category = findById(id);

        if (category.isEmpty()) {
            return false;
        }

        categoryRepository.delete(category.get());
        return true;
    }

    public List<Category> findAll() {

        return categoryRepository.findAll();
    }
}
