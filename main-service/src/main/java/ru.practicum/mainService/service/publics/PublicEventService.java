package ru.practicum.mainService.service.publics;

import ru.practicum.mainService.dto.event.EventFullDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * EventServicePublic
 */
public interface PublicEventService {

    /**
     * получить список событий
     */
    List<EventFullDto> getEvents(String text, List<Long> categories,
                                 Boolean paid, String rangeStart, String rangeEnd,
                                 Boolean onlyAvailable, String sort, Integer from, Integer size, HttpServletRequest request);

    /**
     * получить событие по id
     */
    EventFullDto getEvent(Long eventId, HttpServletRequest request);
}
