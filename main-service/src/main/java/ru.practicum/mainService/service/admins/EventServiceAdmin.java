package ru.practicum.mainService.service.admins;

import ru.practicum.mainService.dto.event.EventFullDto;
import ru.practicum.mainService.dto.event.UpdateAdminRequest;

import java.util.List;

/**
 * EventServiceAdmin
 */
public interface EventServiceAdmin {

    /**
     * Получить события
     */
    List<EventFullDto> getEvents(List<Long> users, List<String> states, List<Long> categories, String rangeStart,
                                 String rangeEnd, Integer from, Integer size);

    /**
     * Обновить событие
     */
    EventFullDto patchEvent(Long eventId, UpdateAdminRequest updateAdminRequest);
}
