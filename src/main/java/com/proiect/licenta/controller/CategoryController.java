package com.proiect.licenta.controller;

import com.proiect.licenta.converter.CategoryConverter;
import com.proiect.licenta.dto.CategoryDTO;
import com.proiect.licenta.exception.ResourceNotFoundException;
import com.proiect.licenta.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryConverter categoryConverter;

    @PostMapping("/create")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {

        return new ResponseEntity<>(
                categoryConverter.modelToDTO(categoryService.save(categoryConverter.dtoToModel(categoryDTO))), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {

        return new ResponseEntity<>(
                categoryService.findAll()
                        .stream()
                        .map(category -> categoryConverter.modelToDTO(category))
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable(name = "id") Long id) {

        var category = categoryService.findById(id);

        if (category.isEmpty()) {

            throw new ResourceNotFoundException(String.format("No such test with id: %d", id));
        }

        return new ResponseEntity<>(categoryConverter.modelToDTO(category.get()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCategory(@PathVariable(name = "id") Long id) {

        if (!categoryService.deleteById(id)) {

            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO) {

        return new ResponseEntity<>(
                categoryConverter.modelToDTO(categoryService.update(categoryConverter.dtoToModel(categoryDTO))),
                HttpStatus.ACCEPTED
        );
    }
}
