package ru.practicum.mainService.service.impl.publics;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.StatsClient;
import ru.practicum.mainService.dto.event.EventFullDto;
import ru.practicum.mainService.dto.event.EventMapper;
import ru.practicum.mainService.error.exception.NotFoundEventException;
import ru.practicum.mainService.model.Event;
import ru.practicum.mainService.model.QEvent;
import ru.practicum.mainService.repository.publics.PublicEventRepository;
import ru.practicum.mainService.service.publics.PublicEventService;
import ru.practicum.stats.dto.HitDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * EventServicePublicImpl
 */

@Service
public class PublicEventServiceImpl implements PublicEventService {

    private final PublicEventRepository repository;

    private final StatsClient statsClient;

    @Autowired
    public PublicEventServiceImpl(PublicEventRepository repository, StatsClient statsClient) {
        this.repository = repository;
        this.statsClient = statsClient;
    }

    @Override
    public List<EventFullDto> getEvents(String text, List<Long> categories,
                                        Boolean paid, String rangeStart,
                                        String rangeEnd, Boolean onlyAvailable,
                                        String sort, Integer from, Integer size, HttpServletRequest request) {

        sendHit(request);

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
        List<Event> filteredEvents = repository.findAll(filter, pageRequest).toList();

        return filteredEvents.stream().map(EventMapper::toFullEventDto).collect(Collectors.toList());
    }

    @Override
    public EventFullDto getEvent(Long eventId, HttpServletRequest request) {

        Event event = repository.findById(eventId).orElseThrow(() -> new NotFoundEventException(String.format("Event id=%s not found", eventId)));
        sendHit(request);
        return EventMapper.toFullEventDto(event);
    }

    private void sendHit(HttpServletRequest request) {
        statsClient.sendHit(HitDto.builder().app("main-service").uri(request.getRequestURI()).ip(request.getRemoteAddr()).timestamp(LocalDateTime.now()).build());
    }
}
