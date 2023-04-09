package ru.practicum.mainService.service.impl.admins;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.practicum.mainService.dto.category.CategoryDto;
import ru.practicum.mainService.dto.category.CategoryMapper;
import ru.practicum.mainService.dto.category.NewCategoryDto;
import ru.practicum.mainService.error.exception.CategoryIsNotEmptyException;
import ru.practicum.mainService.error.exception.CategoryNameDuplicateException;
import ru.practicum.mainService.model.Category;
import ru.practicum.mainService.model.Event;
import ru.practicum.mainService.repository.admins.CategoryRepositoryAdmin;
import ru.practicum.mainService.repository.admins.EventRepositoryAdmin;
import ru.practicum.mainService.service.admins.CategoryServiceAdmin;

import java.util.List;

/**
 * CategoryServiceAdminImpl
 */

@Service
public class CategoryServiceAdminImpl implements CategoryServiceAdmin {

    private final CategoryRepositoryAdmin repository;

    private final EventRepositoryAdmin eventRepository;

    @Autowired
    public CategoryServiceAdminImpl(CategoryRepositoryAdmin repository, EventRepositoryAdmin eventRepository) {
        this.repository = repository;
        this.eventRepository = eventRepository;
    }

    @Override
    public CategoryDto postCategory(NewCategoryDto newCategoryDto) {
        List<Category> allByName = repository.findAllByName(newCategoryDto.getName());
        if (!allByName.isEmpty()) {
            throw new CategoryNameDuplicateException("Обратите внимание: имя категории должно быть уникальным: "
                    + newCategoryDto.getName());
        }
        Category category = new Category();
        category.setName(newCategoryDto.getName());
        Category savedCategory = repository.save(category);
        return CategoryMapper.toCategoryDto(savedCategory);
    }

    @Override
    public CategoryDto patchCategory(Long catId, CategoryDto categoryDto) {
        List<Category> allByName = repository.findAllByName(categoryDto.getName());
        if (!allByName.isEmpty()) {
            throw new CategoryNameDuplicateException("Обратите внимание: имя категории должно быть уникальным" +
                    categoryDto.getName());
        }

        Category category = CategoryMapper.fromCategoryDto(categoryDto);
        repository.save(category);
        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    public void deleteCategory(Long catId) {
        List<Event> events = eventRepository.findAllByCategoryId(catId);
        if (!events.isEmpty()) {
            throw new CategoryIsNotEmptyException("Обратите внимание: с категорией не должно быть связано ни одного события.");
        }
        repository.deleteById(catId);
    }
}
