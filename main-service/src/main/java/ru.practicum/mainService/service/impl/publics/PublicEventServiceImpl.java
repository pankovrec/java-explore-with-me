package ru.practicum.mainService.service.impl.publics;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.mainService.dto.event.EventFullDto;
import ru.practicum.mainService.dto.event.EventMapper;
import ru.practicum.mainService.model.Event;
import ru.practicum.mainService.model.QEvent;
import ru.practicum.mainService.repository.publics.PublicEventRepository;
import ru.practicum.mainService.service.publics.PublicEventService;
import ru.practicum.mainService.service.stats.StatsEventService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
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

        List<Event> filteredEvents;
        QEvent qEvent = QEvent.event;
        BooleanExpression filter = qEvent.isNotNull();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ROOT);
        if (!text.isEmpty()) {
            filter = filter.and(qEvent.annotation.toUpperCase().contains(text.toUpperCase()).or(qEvent.description.toUpperCase().contains(text.toUpperCase()).or(qEvent.title.toUpperCase().contains(text.toUpperCase()))));
        }

        if (categories != null) {
            filter = filter.and(qEvent.category.id.in(categories));
        }
        if (paid != null) {
            filter = filter.and(qEvent.paid.eq(paid));
        }
        if (rangeEnd != null && rangeStart != null) {
            filter = filter.and(qEvent.eventDate.between(LocalDateTime.parse(rangeStart, formatter), LocalDateTime.parse(rangeEnd, formatter)));
        }
        Pageable pageRequest = PageRequest.of((from / size), size);
        filteredEvents = repository.findAll(filter, pageRequest).toList();
        Map<Long, Long> stats = statsEventService.getStats(filteredEvents, false);
        statsEventService.postViews(stats, filteredEvents);

        return filteredEvents.stream().map(EventMapper::toFullEventDto).collect(Collectors.toList());
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
