package ru.practicum.mainService.error.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.mainService.controller.admins.AdminEventController;
import ru.practicum.mainService.controller.privates.PrivateEventController;
import ru.practicum.mainService.error.ApiError;
import ru.practicum.mainService.error.exception.EventDateIncorrectException;
import ru.practicum.mainService.error.exception.IncorrectStateEventAdminException;
import ru.practicum.mainService.error.exception.IncorrectStateEventException;
import ru.practicum.mainService.error.exception.LimitRequestParticipantException;

@RestControllerAdvice(assignableTypes = {PrivateEventController.class, AdminEventController.class})
public class ErrorEventHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ApiError> incorrectStateException(final IncorrectStateEventException e) {
        ApiError error = new ApiError(e.getMessage());
        error.setReason("Incorrectly made request.");
        error.setStatus(HttpStatus.CONFLICT);
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ApiError> participationLimitException(final LimitRequestParticipantException e) {
        ApiError error = new ApiError(e.getMessage());
        error.setReason("For the requested operation the conditions are not met.");
        error.setStatus(HttpStatus.CONFLICT);
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ApiError> incorrectStateAdminException(final IncorrectStateEventAdminException e) {
        ApiError error = new ApiError(e.getMessage());
        error.setReason("For the requested operation the conditions are not met.");
        error.setStatus(HttpStatus.CONFLICT);
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ApiError> incorrectDateException(final EventDateIncorrectException e) {
        ApiError error = new ApiError(e.getMessage());
        error.setReason("For the requested operation the conditions are not met.");
        error.setStatus(HttpStatus.CONFLICT);
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}