package ru.practicum.mainService.controller.privates;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainService.dto.event.*;
import ru.practicum.mainService.dto.request.ParticipationRequestDto;
import ru.practicum.mainService.service.privates.EventServicePrivate;
import ru.practicum.mainService.validator.EventValidator;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * Event controller private
 */

@RestController
@Slf4j

public class EventControllerPrivate {

    private final EventServicePrivate eventService;

    private final EventValidator validator;

    @Autowired
    public EventControllerPrivate(EventServicePrivate eventService, EventValidator validator) {
        this.eventService = eventService;
        this.validator = validator;
    }

    @GetMapping(path = "users/{userId}/events")
    public List<EventShortDto> getEvents(@PathVariable(name = "userId") Long userId,
                                         @RequestParam(name = "from", defaultValue = "0") Integer from,
                                         @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Get list events by userId={}, from={}, size={}", userId, from, size);
        return eventService.getEvents(userId, from, size);
    }

    @GetMapping(path = "users/{userId}/events/{eventId}")
    public EventFullDto getEventByEventId(@PathVariable(name = "userId") Long userId,
                                          @PathVariable(name = "eventId") Long eventId) {
        log.info("Get full event by userId={} and id={}", userId, eventId);
        return eventService.getEventByEventId(userId, eventId);
    }

    @GetMapping(path = "users/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getEventParticipants(@Positive @PathVariable(name = "userId") Long userId,
                                                              @Positive @PathVariable(name = "eventId") Long eventId) {
        log.info("Get request by userId={} and eventId={}", userId, eventId);
        return eventService.getEventParticipants(userId, eventId);
    }

    @PostMapping(path = "users/{userId}/events")
    @Transactional
    public ResponseEntity<EventFullDto> postEvent(@Positive @PathVariable(name = "userId") Long userId,
                                                  @Valid @NotNull @RequestBody NewEventDto newEventDto) {
        validator.validateEvent(newEventDto);
        EventFullDto event = eventService.postEvent(userId, newEventDto);
        log.info("Post by userId={} new event={}", userId, newEventDto);
        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }

    @PatchMapping(path = "users/{userId}/events/{eventId}")
    @Transactional
    public EventFullDto patchUserEventById(@Positive @PathVariable(name = "userId") Long userId,
                                           @Positive @PathVariable(name = "eventId") Long eventId,
                                           @NotNull @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        validator.validateEvent(updateEventUserRequest);
        log.info("Patch event id={} by userId={}, update event={}", eventId, userId, updateEventUserRequest);
        return eventService.patchUserEventById(userId, eventId, updateEventUserRequest);
    }

    @PatchMapping(path = "users/{userId}/events/{eventId}/requests")
    @Transactional
    public EventRequestStatusUpdateResult changeRequestStatus(@Positive @PathVariable(name = "userId") Long userId,
                                                              @Positive @PathVariable(name = "eventId") Long eventId,
                                                              @NotNull @RequestBody EventRequestStatusUpdateRequest
                                                                      eventRequestStatusUpdateRequest) {
        log.info("Patch requests userId={}, eventId={}, and eventRequestStatusUpdateRequest={}", userId, eventId,
                eventRequestStatusUpdateRequest);
        return eventService.changeRequestStatus(userId, eventId, eventRequestStatusUpdateRequest);
    }
}
