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
        List<Category> check = repository.findAllByName(newCategoryDto.getName());
        checkDuplicateName(newCategoryDto, check);
        Category category = new Category();
        category.setName(newCategoryDto.getName());
        Category prePostedCategory = repository.save(category);
        return CategoryMapper.toCategoryDto(prePostedCategory);
    }

    @Override
    public CategoryDto patchCategory(Long catId, CategoryDto categoryDto) {
        List<Category> check = repository.findAllByName(categoryDto.getName());
        checkDuplicateName(categoryDto, check);
        Category category = CategoryMapper.fromCategoryDto(categoryDto);
        repository.save(category);
        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    public void deleteCategory(Long catId) {
        List<Event> check = eventRepository.findAllByCategoryId(catId);
        checkEmptyCategory(check);
        repository.deleteById(catId);
    }

    private static void checkEmptyCategory(List<Event> check) {
        if (!check.isEmpty()) {
            throw new NotEmptyCategoryException("с категорией не должно быть связано ни одного события.");
        }
    }

    private static void checkDuplicateName(NewCategoryDto newCategoryDto, List<Category> check) {
        if (!check.isEmpty()) {
            throw new DuplicateNameCategoryException("имя категории должно быть уникальным: "
                    + newCategoryDto.getName());
        }
    }

    private static void checkDuplicateName(CategoryDto newCategoryDto, List<Category> check) {
        if (!check.isEmpty()) {
            throw new DuplicateNameCategoryException("имя категории должно быть уникальным: "
                    + newCategoryDto.getName());
        }
    }
}
