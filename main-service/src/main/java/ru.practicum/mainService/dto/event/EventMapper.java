package ru.practicum.mainService.dto.event;

import ru.practicum.mainService.model.Event;
import ru.practicum.mainService.model.Location;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Event mapper
 */

public class EventMapper {

    public static EventShortDto toShortEventDto(Event entity) {
        EventShortDto dto = new EventShortDto();
        dto.setId(entity.getId());
        dto.setAnnotation(entity.getAnnotation());
        dto.setCategory(EventShortDto.CategoryDto.categoryToCategoryDto(entity.getCategory()));
        dto.setConfirmedRequests(entity.getConfirmedRequests());
        dto.setEventDate(entity.getEventDate());
        dto.setInitiator(EventShortDto.UserShortDto.userToUserShortDto(entity.getInitiator()));
        dto.setPaid(entity.getPaid());
        dto.setTitle(entity.getTitle());
        dto.setViews(entity.getViews());
        return dto;
    }

    public static Event fromEventDto(NewEventDto dto) {
        Event event = new Event();
        event.setAnnotation(dto.getAnnotation());
        event.setDescription(dto.getDescription());
        event.setEventDate(dto.getEventDate());
        event.setLat(dto.getLocation().getLat());
        event.setLon(dto.getLocation().getLon());
        event.setPaid(dto.getPaid());
        event.setParticipantLimit(dto.getParticipantLimit());
        event.setRequestModeration(dto.getRequestModeration());
        event.setTitle(dto.getTitle());
        return event;
    }

    public static EventFullDto toFullEventDto(Event entity) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ROOT);

        EventFullDto dto = new EventFullDto();
        dto.setId(entity.getId());
        dto.setAnnotation(entity.getAnnotation());
        dto.setCategory(EventFullDto.CategoryDto.categoryToCategoryDto(entity.getCategory()));
        dto.setConfirmedRequests(entity.getConfirmedRequests());
        dto.setCreatedOn(entity.getCreatedOn().format(formatter));
        dto.setDescription(entity.getDescription());
        dto.setEventDate(entity.getEventDate().format(formatter));
        dto.setInitiator(EventFullDto.UserShortDto.userToUserShortDto(entity.getInitiator()));
        dto.setLocation(new Location(entity.getLat(), entity.getLon()));
        dto.setPaid(entity.getPaid());
        dto.setParticipantLimit(entity.getParticipantLimit());
        if (entity.getPublishedOn() != null)
            dto.setPublishedOn(entity.getPublishedOn().format(formatter));
        dto.setRequestModeration(entity.getRequestModeration());
        dto.setState(entity.getState());
        dto.setTitle(entity.getTitle());
        dto.setViews(entity.getViews());
        return dto;
    }
}
