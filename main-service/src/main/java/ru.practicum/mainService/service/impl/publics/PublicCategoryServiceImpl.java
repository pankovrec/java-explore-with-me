package ru.practicum.mainService.service.impl.publics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.mainService.dto.category.CategoryDto;
import ru.practicum.mainService.dto.category.CategoryMapper;
import ru.practicum.mainService.error.exception.NotFoundCategoryException;
import ru.practicum.mainService.model.Category;
import ru.practicum.mainService.repository.publics.PublicCategoryRepository;
import ru.practicum.mainService.service.publics.PublicCategoryService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * CategoryServicePublicImpl
 */

@Service
public class PublicCategoryServiceImpl implements PublicCategoryService {

    private final PublicCategoryRepository repository;

    @Autowired
    public PublicCategoryServiceImpl(PublicCategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        Pageable pageRequest = PageRequest.of((from / size), size);
        Page<Category> categories = repository.findAll(pageRequest);
        return categories.stream().map(CategoryMapper::toCategoryDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategory(Long catId) {
        if (repository.findById(catId).isEmpty()) {
            throw new NotFoundCategoryException("Категория не найдена или недоступна " + catId);
        }
        return CategoryMapper.toCategoryDto(repository.findById(catId).get());
    }
}
