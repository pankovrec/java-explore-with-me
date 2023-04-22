package ru.practicum.mainService.service.impl.publics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.practicum.mainService.dto.event.EventFullDto;
import ru.practicum.mainService.dto.event.EventMapper;
import ru.practicum.mainService.dto.event.EventSpecifications;
import ru.practicum.mainService.model.Event;
import ru.practicum.mainService.repository.publics.EventRepositoryPublic;
import ru.practicum.mainService.service.api.EventStatsService;
import ru.practicum.mainService.service.publics.EventServicePublic;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * EventServicePublicImpl
 */

@Service
public class EventServicePublicImpl implements EventServicePublic {

    private final EventRepositoryPublic repository;

    private final EventStatsService eventStatsService;

    @Autowired
    public EventServicePublicImpl(EventRepositoryPublic repository, EventStatsService eventStatsService) {
        this.repository = repository;
        this.eventStatsService = eventStatsService;
    }

    @Override
    public List<EventFullDto> getEvents(String text, List<Long> categories,
                                        Boolean paid, String rangeStart,
                                        String rangeEnd, Boolean onlyAvailable,
                                        String sort, Integer from, Integer size, HttpServletRequest request) {

        eventStatsService.postStats(request);

        Specification<Event> spec = EventSpecifications.byText(text)
                .and(EventSpecifications.byCategories(categories))
                .and(EventSpecifications.byPaid(paid))
                .and(EventSpecifications.byRangeStart(rangeStart))
                .and(EventSpecifications.byRangeEnd(rangeEnd))
                .and(EventSpecifications.byOnlyAvailable(onlyAvailable));
        Pageable pageRequest = PageRequest.of((from / size), size);

        List<Event> events = repository.findAll(spec, pageRequest);
        Map<Long, Long> stats = eventStatsService.getStats(events, false);
        eventStatsService.setViews(stats, events);

        return events.stream().map(EventMapper::toFullEventDto).collect(Collectors.toList());
    }

    @Override
    public EventFullDto getEventById(Long eventId, HttpServletRequest request) {

        eventStatsService.postStats(request);

        Optional<Event> eventOptional = repository.findById(eventId);
        Event event = eventOptional.get();
        Map<Long, Long> stats = eventStatsService.getStats(List.of(event), false);
        event.setViews(stats.get(eventId));

        return EventMapper.toFullEventDto(event);
    }

}
