package com.toufik.controller;

import com.toufik.dto.CategoryDto;
import com.toufik.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@AllArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDto> createcategory(@RequestBody CategoryDto categoryDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryService.save(categoryDto));
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllcategorys() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getcategory(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryService.getcategory(id));
    }
}
