package ru.practicum.mainService.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ru.practicum.StatsClient;
import ru.practicum.mainService.model.Event;
import ru.practicum.mainService.service.api.EventStatsService;
import ru.practicum.stats.dto.HitDto;
import ru.practicum.stats.dto.HitDtoAnswer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * EventStatsServiceImpl
 */

@Service
public class EventStatsServiceImpl implements EventStatsService {

    private final StatsClient statsClient;

    @Autowired
    public EventStatsServiceImpl(StatsClient statsClient) {
        this.statsClient = statsClient;
    }

    @Override
    public Map<Long, Long> getStats(List<Event> events, Boolean unique) {

        Map<Long, Long> views = new HashMap<>();
        events.forEach(e -> views.put(e.getId(), 0L));

        Optional<LocalDateTime> startOptional = events.stream()
                .map(Event::getPublishedOn)
                .filter(Objects::nonNull).min(LocalDateTime::compareTo);

        if (startOptional.isEmpty()) {
            return views;
        }

        LocalDateTime start = startOptional.get();

        LocalDateTime end = LocalDateTime.now();

        List<Long> ids = events.stream().map(Event::getId).collect(Collectors.toList());
        List<String> uris = ids.stream().map(id -> "/events/" + id).collect(Collectors.toList());

        ResponseEntity<Object> response = statsClient.getStats(start, end, uris, unique);
        List<HitDtoAnswer> stats;
        ObjectMapper mapper = new ObjectMapper();
        try {
            stats = Arrays.asList(mapper.readValue(mapper.writeValueAsString(response.getBody()), HitDtoAnswer[].class));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        for (Long id : ids) {
            Optional<Long> viewsOptional = stats.stream()
                    .filter(s -> s.getUri().equals("/events/" + id)).map(HitDtoAnswer::getHits).findFirst();
            Long eventViews = viewsOptional.orElse(0L);
            views.put(id, eventViews);
        }

        return views;
    }

    @Override
    public void postStats(HttpServletRequest request) {
        HitDto hitDto = makeHitDto(request);
        statsClient.sendHit(hitDto);
    }

    public void setViews(Map<Long, Long> views, List<Event> events) {
        for (Event event : events) {
            Long view = views.get(event.getId());
            if (view == null) {
                view = 0L;
            }
            event.setViews(view);
        }
    }

    private HitDto makeHitDto(HttpServletRequest request) {

        HitDto hitDto = new HitDto();
        hitDto.setIp(request.getRemoteAddr());
        hitDto.setUri(request.getRequestURI());
        hitDto.setTimestamp(LocalDateTime.now());
        hitDto.setApp("main-service");

        return hitDto;
    }

}