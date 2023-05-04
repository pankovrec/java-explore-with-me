package ru.practicum.mainService.controller.privates;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainService.dto.event.*;
import ru.practicum.mainService.dto.request.ParticipationRequestDto;
import ru.practicum.mainService.service.privates.PrivateEventService;
import ru.practicum.mainService.validator.EventValidate;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * Event controller private
 */

@RestController
@Slf4j
@Validated
@RequestMapping(path = "user/events")

public class PrivateEventController {

    private final PrivateEventService eventService;

    private final EventValidate validator;

    @Autowired
    public PrivateEventController(PrivateEventService eventService, EventValidate validator) {
        this.eventService = eventService;
        this.validator = validator;
    }

    @GetMapping(path = "users/{userId}/events")
    public List<EventShortDto> getEvents(@NotNull @PathVariable(name = "userId") Long userId,
                                         @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                         @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Получен список событий пользователя {}", userId);
        return eventService.getEvents(userId, from, size);
    }

    @GetMapping(path = "users/{userId}/events/{eventId}")
    public EventFullDto getEvent(@Positive @PathVariable(name = "userId") Long userId,
                                 @Positive @PathVariable(name = "eventId") Long eventId) {
        log.info("Получено событие с id={}", eventId);
        return eventService.getEvent(userId, eventId);
    }

    @GetMapping(path = "users/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getParticipants(@Positive @PathVariable(name = "userId") Long userId,
                                                         @Positive @PathVariable(name = "eventId") Long eventId) {
        log.info("Получен запрос на участие от пользователя с Id={} в событии с Id={}", userId, eventId);
        return eventService.getParticipants(userId, eventId);
    }

    @PostMapping(path = "users/{userId}/events")
    @Transactional
    public ResponseEntity<EventFullDto> postEvent(@Positive @PathVariable(name = "userId") Long userId,
                                                  @Valid @NotNull @RequestBody NewEventDto newEventDto) {
        validator.eventValidateDate(newEventDto);
        EventFullDto event = eventService.postEvent(userId, newEventDto);
        log.info("Опубликовано новое событие ={}", newEventDto);
        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }

    @PatchMapping(path = "users/{userId}/events/{eventId}")
    @Transactional
    public EventFullDto patchEvent(@Positive @PathVariable(name = "userId") Long userId,
                                   @Positive @PathVariable(name = "eventId") Long eventId,
                                   @NotNull @RequestBody UpdateUserRequest updateUserRequest) {
        validator.eventValidateDate(updateUserRequest);
        log.info("Обновлено событие с  Id={}", eventId);
        return eventService.patchEvent(userId, eventId, updateUserRequest);
    }

    @PatchMapping(path = "users/{userId}/events/{eventId}/requests")
    @Transactional
    public RequestStatusUpdateResult changeStatusOfRequest(@Positive @PathVariable(name = "userId") Long userId,
                                                           @Positive @PathVariable(name = "eventId") Long eventId,
                                                           @NotNull @RequestBody RequestStatusUpdateRequest
                                                                   requestStatusUpdateRequest) {
        log.info("Изменился статус запроса на участие в событии с Id={}", eventId);
        return eventService.changeStatusOfRequest(userId, eventId, requestStatusUpdateRequest);
    }
}