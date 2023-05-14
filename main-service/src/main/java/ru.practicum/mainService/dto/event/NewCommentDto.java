package ru.practicum.mainService.dto.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * New Comment Dto.
 */

@NoArgsConstructor
@Setter
@Getter
public class NewCommentDto {
    @NotNull
    @NotBlank
    @Size(min = 3, max = 300)
    private String text;
}
