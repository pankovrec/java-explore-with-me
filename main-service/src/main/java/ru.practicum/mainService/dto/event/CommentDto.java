package ru.practicum.mainService.dto.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Comment Dto
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CommentDto {
    private Long id;
    private String text;
    private Long eventId;
    private String userName;
    private LocalDateTime created;
    private LocalDateTime updated;
}
