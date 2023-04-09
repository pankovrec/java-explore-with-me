package ru.practicum.mainService.controller.publics;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainService.dto.category.CategoryDto;
import ru.practicum.mainService.service.publics.CategoryServicePublic;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * Category controller public
 */

@RestController
@Slf4j
@RequestMapping(path = "/categories")
@Validated

public class CategoryControllerPublic {

    private final CategoryServicePublic categoryService;

    @Autowired
    public CategoryControllerPublic(CategoryServicePublic categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping()
    public List<CategoryDto> getCategories(@PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                           @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Get list categories from={}, size={}", from, size);
        return categoryService.getCategories(from, size);
    }

    @GetMapping(path = "/{catId}")
    public CategoryDto getCategoryById(@Positive @PathVariable(name = "catId") Long catId) {
        log.info("Get category by ID={}", catId);
        return categoryService.getCategoryById(catId);
    }
}
