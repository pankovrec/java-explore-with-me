package ru.practicum.mainService.error.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.mainService.controller.privates.RequestControllerPrivate;
import ru.practicum.mainService.error.ApiError;
import ru.practicum.mainService.error.exception.InvalidRequestException;
import ru.practicum.mainService.error.exception.RequestAlreadyExistException;

@RestControllerAdvice(assignableTypes = {RequestControllerPrivate.class})
public class RequestErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ApiError> handleRequestAlreadyExistException(final RequestAlreadyExistException e) {
        ApiError error = new ApiError(e.getMessage());
        error.setReason("Integrity constraint has been violated.");
        error.setStatus(HttpStatus.CONFLICT);
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ApiError> handleInvalidRequestException(final InvalidRequestException e) {
        ApiError error = new ApiError(e.getMessage());
        error.setReason("Integrity constraint has been violated.");
        error.setStatus(HttpStatus.CONFLICT);
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}
