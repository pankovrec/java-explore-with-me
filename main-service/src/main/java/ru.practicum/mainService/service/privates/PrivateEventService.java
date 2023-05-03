package ru.practicum.mainService.service.privates;

import ru.practicum.mainService.dto.event.*;
import ru.practicum.mainService.dto.request.ParticipationRequestDto;

import java.util.List;

/**
 * EventServicePrivate
 */
public interface PrivateEventService {

    /**
     * Получить список событий
     */
    List<EventShortDto> getEvents(Long userId, Integer from, Integer size);

    /**
     * получить событие по id
     */
    EventFullDto getEvent(Long userId, Long eventId);

    /**
     * получить список участников события
     */
    List<ParticipationRequestDto> getParticipants(Long userId, Long eventId);

    /**
     * добавить событие
     */
    EventFullDto postEvent(Long userId, NewEventDto eventDto);

    /**
     * обновить событие по id
     */
    EventFullDto patchEvent(Long userId, Long eventId, UpdateUserRequest eventDto);

    /**
     * обновить статус запроса на участие
     */
    RequestStatusUpdateResult changeStatusOfRequest(Long userId, Long eventId, RequestStatusUpdateRequest requestStatusUpdateRequest);
}
