package ru.practicum.mainService.dto.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.mainService.model.StateAction;

import javax.validation.constraints.NotNull;

/**
 * UpdateEventUserRequest
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {
    private String eventDate;
    @NotNull
    private StateAction stateAction;
}
