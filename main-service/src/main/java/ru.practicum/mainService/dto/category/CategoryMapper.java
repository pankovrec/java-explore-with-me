package ru.practicum.mainService.dto.category;

import ru.practicum.mainService.model.Category;

/**
 * Category mapper
 */

public class CategoryMapper {

    public static CategoryDto toCategoryDto(Category entity) {
        return new CategoryDto(entity.getId(),
                entity.getName());
    }

    public static Category fromCategoryDto(CategoryDto categoryDto) {
        return new Category(categoryDto.getId(), categoryDto.getName());
    }
}
