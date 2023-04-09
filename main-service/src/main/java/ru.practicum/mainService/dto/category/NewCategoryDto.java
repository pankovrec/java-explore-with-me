package ru.practicum.mainService.dto.category;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * newCategoryDto
 */

@Getter
@Setter
public class NewCategoryDto {
    @NotNull
    private String name;
}
