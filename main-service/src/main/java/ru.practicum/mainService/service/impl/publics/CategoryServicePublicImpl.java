package ru.practicum.mainService.service.impl.publics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.mainService.dto.category.CategoryDto;
import ru.practicum.mainService.dto.category.CategoryMapper;
import ru.practicum.mainService.error.exception.CategoryNotFoundException;
import ru.practicum.mainService.model.Category;
import ru.practicum.mainService.repository.publics.CategoryRepositoryPublic;
import ru.practicum.mainService.service.publics.CategoryServicePublic;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * CategoryServicePublicImpl
 */

@Service
public class CategoryServicePublicImpl implements CategoryServicePublic {

    private final CategoryRepositoryPublic repository;

    @Autowired
    public CategoryServicePublicImpl(CategoryRepositoryPublic repository) {
        this.repository = repository;
    }

    @Override
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        Pageable pageRequest = PageRequest.of((from / size), size);
        Page<Category> categories = repository.findAll(pageRequest);
        return categories.stream().map(CategoryMapper::toCategoryDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Long catId) {
        Optional<Category> category = repository.findById(catId);
        if (category.isEmpty()) {
            throw new CategoryNotFoundException("Категория не найдена или недоступна " + catId);
        }
        return CategoryMapper.toCategoryDto(category.get());
    }
}
