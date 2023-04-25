package ru.practicum.mainService.dto.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.mainService.model.Status;

import java.util.List;

/**
 * EventRequestStatusUpdateRequest
 */

@NoArgsConstructor
@Setter
@Getter
public class RequestStatusUpdateRequest {
    List<Long> requestIds;
    private Status status;
}
