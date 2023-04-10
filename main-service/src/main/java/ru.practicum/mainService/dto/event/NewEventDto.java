package ru.practicum.mainService.dto.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.mainService.model.Location;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * NewEventDto
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewEventDto {
    @NotNull
    private String annotation;
    private Long category;
    @NotNull
    private String description;
    private LocalDateTime eventDate;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    @NotNull
    private String title;
}
