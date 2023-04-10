package ru.practicum.mainService.dto.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.mainService.model.Location;
import ru.practicum.mainService.model.StateAction;

import java.time.LocalDateTime;

/**
 * UpdateEventAdminRequest
 */

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UpdateEventAdminRequest {
    private Long id;
    private String annotation;
    private Long category;
    private String description;
    private LocalDateTime eventDate;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private StateAction stateAction;
    private String title;
}
