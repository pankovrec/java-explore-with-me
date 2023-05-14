package ru.practicum.mainService.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Comment Dto
 */

@Getter
@Setter
@NoArgsConstructor

public class CommentDto {

    private Long id;

    private String author;

    private Long event;

    @NotNull
    private String text;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;
}