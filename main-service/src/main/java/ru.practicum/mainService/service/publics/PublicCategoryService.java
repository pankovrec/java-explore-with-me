package ru.practicum.mainService.service.publics;

import ru.practicum.mainService.dto.category.CategoryDto;

import java.util.List;

/**
 * CategoryServicePublic
 */
public interface PublicCategoryService {

    /**
     * получить список категорий
     */
    List<CategoryDto> getCategories(Integer from, Integer size);

    /**
     * получить категорию по id
     */
    CategoryDto getCategory(Long catId);
}
