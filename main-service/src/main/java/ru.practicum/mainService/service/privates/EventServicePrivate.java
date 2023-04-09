package ru.practicum.mainService.service.privates;

import ru.practicum.mainService.dto.event.*;
import ru.practicum.mainService.dto.request.ParticipationRequestDto;

import java.util.List;

/**
 * EventServicePrivate
 */
public interface EventServicePrivate {

    /**
     * Получить список событий
     */
    List<EventShortDto> getEvents(Long userId, Integer from, Integer size);

    /**
     * получить событие по id
     */
    EventFullDto getEventByEventId(Long userId, Long eventId);

    /**
     * получить список участников события
     */
    List<ParticipationRequestDto> getEventParticipants(Long userId, Long eventId);

    /**
     * добавить событие
     */
    EventFullDto postEvent(Long userId, NewEventDto eventDto);

    /**
     * обновить событие по id
     */
    EventFullDto patchUserEventById(Long userId, Long eventId, UpdateEventUserRequest eventDto);

    /**
     * обновить статус запроса на участие
     */
    EventRequestStatusUpdateResult changeRequestStatus(Long userId, Long eventId, EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest);
}
