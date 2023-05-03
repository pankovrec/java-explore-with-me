package ru.practicum.mainService.service.admins;

import ru.practicum.mainService.dto.category.CategoryDto;
import ru.practicum.mainService.dto.category.NewCategoryDto;

/**
 * CategoryServiceAdmin
 */
public interface AdminCategoryService {
    /**
     * Публикация категории
     */
    CategoryDto postCategory(NewCategoryDto newCategoryDto);

    /**
     * Обновление категории
     */
    CategoryDto patchCategory(Long catId, CategoryDto categoryDto);

    /**
     * Удаление категории
     */
    void deleteCategory(Long catId);
}
