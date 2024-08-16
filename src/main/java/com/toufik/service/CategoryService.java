package com.toufik.service;

import com.toufik.dto.CategoryDto;
import com.toufik.mapper.CategoryMapper;
import com.toufik.model.Category;
import com.toufik.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional
    public CategoryDto save(CategoryDto categoryDto) {
        Category save = categoryRepository.save(categoryMapper.mapDtoTocategory(categoryDto));
        categoryDto.setId(save.getId());
        return categoryDto;
    }

    @Transactional(readOnly = true)
    public List<CategoryDto> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::mapcategoryToDto)
                .collect(toList());
    }

    public CategoryDto getcategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No category found with ID - " + id));
        return categoryMapper.mapcategoryToDto(category);
    }
}
