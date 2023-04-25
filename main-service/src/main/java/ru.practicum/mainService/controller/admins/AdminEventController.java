package ru.practicum.mainService.controller.admins;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainService.dto.event.EventFullDto;
import ru.practicum.mainService.dto.event.UpdateAdminRequest;
import ru.practicum.mainService.service.admins.EventServiceAdmin;
import ru.practicum.mainService.validator.EventValidate;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * Event controller admin
 */

@RestController
@RequestMapping(path = "admin/events")
@Slf4j
@Validated

public class AdminEventController {

    private final EventServiceAdmin eventService;

    private final EventValidate validator;

    @Autowired
    public AdminEventController(EventServiceAdmin eventService, EventValidate validator) {
        this.eventService = eventService;
        this.validator = validator;
    }

    @GetMapping
    public List<EventFullDto> getEventsAdmin(@RequestParam(name = "users", required = false) List<Long> users,
                                             @RequestParam(name = "states", required = false) List<String> states,
                                             @RequestParam(name = "categories", required = false) List<Long> categories,
                                             @RequestParam(name = "rangeStart", required = false) String rangeStart,
                                             @RequestParam(name = "rangeEnd", required = false) String rangeEnd,
                                             @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                             @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Get list events from={}, size={}", from, size);
        return eventService.getEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping(path = "/{eventId}")
    @Transactional
    public EventFullDto patchEventAdmin(@NotNull @PathVariable(name = "eventId") Long eventId,
                                        @NotNull @RequestBody UpdateAdminRequest updateAdminRequest) {
        validator.eventValidateDate(updateAdminRequest);
        log.info("Patch event id={}, eventUpd={}", eventId, updateAdminRequest);
        return eventService.patchEvent(eventId, updateAdminRequest);
    }
}