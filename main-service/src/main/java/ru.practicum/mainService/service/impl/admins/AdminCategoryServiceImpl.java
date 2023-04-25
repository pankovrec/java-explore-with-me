package ru.practicum.mainService.service.impl.admins;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.mainService.dto.category.CategoryDto;
import ru.practicum.mainService.dto.category.CategoryMapper;
import ru.practicum.mainService.dto.category.NewCategoryDto;
import ru.practicum.mainService.error.exception.DuplicateNameCategoryException;
import ru.practicum.mainService.error.exception.NotEmptyCategoryException;
import ru.practicum.mainService.model.Category;
import ru.practicum.mainService.model.Event;
import ru.practicum.mainService.repository.admins.AdminCategoryRepository;
import ru.practicum.mainService.repository.admins.AdminEventRepository;
import ru.practicum.mainService.service.admins.AdminCategoryService;

import java.util.List;

/**
 * CategoryServiceAdminImpl
 */

@Service
public class AdminCategoryServiceImpl implements AdminCategoryService {

    private final AdminCategoryRepository repository;

    private final AdminEventRepository eventRepository;

    @Autowired
    public AdminCategoryServiceImpl(AdminCategoryRepository repository, AdminEventRepository eventRepository) {
        this.repository = repository;
        this.eventRepository = eventRepository;
    }

    @Override
    public CategoryDto postCategory(NewCategoryDto newCategoryDto) {
        List<Category> allByName = repository.findAllByName(newCategoryDto.getName());
        if (!allByName.isEmpty()) {
            throw new DuplicateNameCategoryException("имя категории должно быть уникальным: "
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
            throw new DuplicateNameCategoryException("имя категории должно быть уникальным" +
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
            throw new NotEmptyCategoryException("с категорией не должно быть связано ни одного события.");
        }
        repository.deleteById(catId);
    }
}
