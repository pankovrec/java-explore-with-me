package ru.practicum.mainService.dto.request;

import ru.practicum.mainService.model.Request;

/**
 * RequestMapper
 */

public class RequestMapper {

    public static ParticipationRequestDto toRequestDto(Request request) {
        ParticipationRequestDto dto = new ParticipationRequestDto();
        dto.setId(request.getId());
        dto.setRequester(request.getRequester().getId());
        dto.setEvent(request.getEvent().getId());
        dto.setCreated(request.getCreated());
        dto.setStatus(request.getStatus().name());
        return dto;
    }
}
