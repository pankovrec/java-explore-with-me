package ru.practicum.mainService.controller.privates;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainService.dto.request.ParticipationRequestDto;
import ru.practicum.mainService.service.privates.PrivateRequestService;

import javax.transaction.Transactional;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * Request controller private
 */

@RestController
@Slf4j
@RequestMapping(path = "/users/{userId}/requests")
@Validated

public class PrivateRequestController {

    private final PrivateRequestService requestService;

    @Autowired
    public PrivateRequestController(PrivateRequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping
    public List<ParticipationRequestDto> getRequests(@Positive @PathVariable(name = "userId") Long userId) {
        log.info("Получен список запросов на участие пользователя с Id={}", userId);
        return requestService.getRequests(userId);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ParticipationRequestDto> postRequest(@Positive @PathVariable(name = "userId") Long userId,
                                                               @Positive @RequestParam(name = "eventId") Long eventId) {
        ParticipationRequestDto requestDto = requestService.postRequest(userId, eventId);
        log.info("Пользователь с Id={} опубликовал запрос на участие в событии с Id={}", userId, eventId);
        return new ResponseEntity<>(requestDto, HttpStatus.CREATED);
    }

    @PatchMapping(path = "{requestId}/cancel")
    @Transactional
    public ParticipationRequestDto cancelRequest(@Positive @PathVariable(name = "userId") Long userId,
                                                 @Positive @PathVariable(name = "requestId") Long requestId) {
        log.info("Пользователь с Id={} отменил запрос с Id={} на участие в событии", userId, requestId);
        return requestService.cancelRequest(userId, requestId);
    }
}
