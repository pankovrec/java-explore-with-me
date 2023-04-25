package ru.practicum.mainService.error.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.mainService.controller.privates.PrivateRequestController;
import ru.practicum.mainService.error.ApiError;
import ru.practicum.mainService.error.exception.ExistRequestException;
import ru.practicum.mainService.error.exception.RequestInvalidException;

@RestControllerAdvice(assignableTypes = {PrivateRequestController.class})
public class ErrorRequestHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ApiError> alreadyExistRequestException(final ExistRequestException e) {
        ApiError error = new ApiError(e.getMessage());
        error.setReason("Integrity constraint has been violated.");
        error.setStatus(HttpStatus.CONFLICT);
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ApiError> requestInvalidException(final RequestInvalidException e) {
        ApiError error = new ApiError(e.getMessage());
        error.setReason("Integrity constraint has been violated.");
        error.setStatus(HttpStatus.CONFLICT);
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}
