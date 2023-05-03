package ru.practicum.mainService.dto.category;

import ru.practicum.mainService.model.Category;

/**
 * Category mapper
 */

public class CategoryMapper {

    public static CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(category.getId(),
                category.getName());
    }

    public static Category fromCategoryDto(CategoryDto categoryDto) {
        return new Category(categoryDto.getId(), categoryDto.getName());
    }
}
