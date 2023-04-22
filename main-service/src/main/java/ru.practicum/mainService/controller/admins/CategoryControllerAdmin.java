package ru.practicum.mainService.controller.admins;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainService.dto.category.CategoryDto;
import ru.practicum.mainService.dto.category.NewCategoryDto;
import ru.practicum.mainService.service.admins.CategoryServiceAdmin;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

/**
 * Category controller admin
 */

@RestController
@RequestMapping(path = "/admin/categories")
@Validated
@Slf4j

public class CategoryControllerAdmin {

    private final CategoryServiceAdmin categoryService;

    @Autowired
    public CategoryControllerAdmin(CategoryServiceAdmin categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<CategoryDto> postCategory(@Valid @RequestBody NewCategoryDto newCategoryDto) {
        CategoryDto categoryDto = categoryService.postCategory(newCategoryDto);
        log.info("Post new category {}", newCategoryDto);
        return new ResponseEntity<>(categoryDto, HttpStatus.CREATED);
    }

    @PatchMapping(path = "/{catId}")
    @Transactional
    public ResponseEntity<CategoryDto> patchCategory(@Positive @PathVariable(name = "catId") Long catId,
                                                     @Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto updateCategory = categoryService.patchCategory(catId, categoryDto);
        log.info("Patch category {} Id={}", categoryDto, catId);
        return new ResponseEntity<>(updateCategory, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{catId}")
    @Transactional
    public ResponseEntity deleteCategory(@Positive @PathVariable(name = "catId") Long catId) {
        categoryService.deleteCategory(catId);
        log.info("Delete сategory with id={}", catId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
