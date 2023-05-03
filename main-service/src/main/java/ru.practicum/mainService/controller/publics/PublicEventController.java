package ru.practicum.mainService.controller.publics;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.StatsClient;
import ru.practicum.mainService.dto.event.EventFullDto;
import ru.practicum.mainService.service.publics.PublicEventService;
import ru.practicum.stats.dto.HitDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * Event controller public
 */

@RestController
@Slf4j
@RequestMapping(path = "/events")
@Validated

public class PublicEventController {

    private final PublicEventService eventService;
    private final StatsClient statsClient;

    @Autowired
    public PublicEventController(PublicEventService eventService, StatsClient statsClient) {
        this.eventService = eventService;
        this.statsClient = statsClient;
    }

    @GetMapping
    public List<EventFullDto> getEvents(@RequestParam(name = "text", required = false) String text,
                                        @RequestParam(name = "categories", required = false) List<Long> categories,
                                        @RequestParam(name = "paid", required = false) Boolean paid,
                                        @RequestParam(name = "rangeStart", required = false) String rangeStart,
                                        @RequestParam(name = "rangeEnd", required = false) String rangeEnd,
                                        @RequestParam(name = "onlyAvailable", required = false) Boolean onlyAvailable,
                                        @RequestParam(name = "sort", required = false) String sort,
                                        @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                        @Positive @RequestParam(name = "size", defaultValue = "10") Integer size,
                                        HttpServletRequest request) {
        log.info("Получен список событий");
        statsClient.sendHit(new HitDto());
        return eventService.getEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size, request);
    }

    @GetMapping(path = "/{id}")
    public EventFullDto getEvent(@NotNull @PathVariable(name = "id") Long id, HttpServletRequest request) {
        log.info("Получено событие с Id={}", id);
        statsClient.sendHit(new HitDto());
        return eventService.getEvent(id, request);
    }
}