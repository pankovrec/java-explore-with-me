package ru.practicum.mainService.controller.privates;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainService.dto.request.ParticipationRequestDto;
import ru.practicum.mainService.service.privates.RequestServicePrivate;

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

public class RequestControllerPrivate {

    private final RequestServicePrivate requestService;

    @Autowired
    public RequestControllerPrivate(RequestServicePrivate requestService) {
        this.requestService = requestService;
    }

    @GetMapping
    public List<ParticipationRequestDto> getUserRequests(@Positive @PathVariable(name = "userId") Long userId) {
        log.info("Get list requests by userId={}", userId);
        return requestService.getUserRequests(userId);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ParticipationRequestDto> postRequestByUser(@Positive @PathVariable(name = "userId") Long userId,
                                                                     @Positive @RequestParam(name = "eventId") Long eventId) {
        ParticipationRequestDto requestDto = requestService.postParticipationRequest(userId, eventId);
        log.info("Post request by userId={} on eventId={}", userId, eventId);
        return new ResponseEntity<>(requestDto, HttpStatus.CREATED);
    }

    @PatchMapping(path = "{requestId}/cancel")
    @Transactional
    public ParticipationRequestDto cancelRequestByUser(@Positive @PathVariable(name = "userId") Long userId,
                                                       @Positive @PathVariable(name = "requestId") Long requestId) {
        log.info("Patch by userId={} on requestId={}", userId, requestId);
        return requestService.cancelRequest(userId, requestId);
    }
}
