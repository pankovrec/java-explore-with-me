package ru.practicum.mainService.service.impl.publics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.practicum.mainService.dto.event.EventFullDto;
import ru.practicum.mainService.dto.event.EventMapper;
import ru.practicum.mainService.model.Event;
import ru.practicum.mainService.repository.publics.PublicEventRepository;
import ru.practicum.mainService.service.impl.EventCriteriaBuilder;
import ru.practicum.mainService.service.publics.PublicEventService;
import ru.practicum.mainService.service.stats.StatsEventService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * EventServicePublicImpl
 */

@Service
public class PublicEventServiceImpl implements PublicEventService {

    private final PublicEventRepository repository;

    private final StatsEventService statsEventService;

    @Autowired
    public PublicEventServiceImpl(PublicEventRepository repository, StatsEventService statsEventService) {
        this.repository = repository;
        this.statsEventService = statsEventService;
    }

    @Override
    public List<EventFullDto> getEvents(String text, List<Long> categories,
                                        Boolean paid, String rangeStart,
                                        String rangeEnd, Boolean onlyAvailable,
                                        String sort, Integer from, Integer size, HttpServletRequest request) {

        statsEventService.postStats(request);

        Specification<Event> filter = EventCriteriaBuilder.byText(text)
                .and(EventCriteriaBuilder.byCategories(categories))
                .and(EventCriteriaBuilder.byPaid(paid))
                .and(EventCriteriaBuilder.byStart(rangeStart))
                .and(EventCriteriaBuilder.byEnd(rangeEnd))
                .and(EventCriteriaBuilder.byAvailable(onlyAvailable));

        Pageable page = PageRequest.of((from / size), size);

        List<Event> events = repository.findAll(filter, page);
        Map<Long, Long> stats = statsEventService.getStats(events, false);
        statsEventService.postViews(stats, events);

        return events.stream().map(EventMapper::toFullEventDto).collect(Collectors.toList());
    }

    @Override
    public EventFullDto getEventById(Long eventId, HttpServletRequest request) {

        statsEventService.postStats(request);

        Optional<Event> eventOptional = repository.findById(eventId);
        Event event = eventOptional.get();
        Map<Long, Long> stats = statsEventService.getStats(List.of(event), false);
        event.setViews(stats.get(eventId));

        return EventMapper.toFullEventDto(event);
    }
}
