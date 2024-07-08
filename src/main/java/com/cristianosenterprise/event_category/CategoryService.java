package com.cristianosenterprise.event_category;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    private final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    public List<CategoryResponse> findAll() {
        return repository.findAll().stream()
                .map(category -> modelMapper.map(category, CategoryResponse.class))
                .collect(Collectors.toList());
    }

    public CategoryResponse findById(Long id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        return modelMapper.map(category, CategoryResponse.class);
    }

    public CategoryResponse create(CategoryRequest categoryRequest) {
        logger.info("Creating category with data: {}", categoryRequest);
        Category category = modelMapper.map(categoryRequest, Category.class);

        Category savedCategory = repository.save(category);
        logger.info("Category created with id: {}", savedCategory.getId());

        return modelMapper.map(savedCategory, CategoryResponse.class);
    }

    public CategoryResponse update(Long id, CategoryRequest categoryRequest) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        modelMapper.map(categoryRequest, category);
        category.setId(id);

        Category updatedCategory = repository.save(category);

        return modelMapper.map(updatedCategory, CategoryResponse.class);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
