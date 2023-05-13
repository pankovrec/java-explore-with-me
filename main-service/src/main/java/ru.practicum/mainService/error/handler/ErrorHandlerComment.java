package ru.practicum.mainService.error.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.mainService.controller.admins.AdminCommentController;
import ru.practicum.mainService.controller.privates.PrivateCommentController;
import ru.practicum.mainService.controller.publics.PublicCommentController;
import ru.practicum.mainService.error.ApiError;
import ru.practicum.mainService.error.exception.IncorrectStateEventException;
import ru.practicum.mainService.error.exception.NotFoundCommentException;
import ru.practicum.mainService.error.exception.RequestInvalidException;

/**
 * Error Comment Handler.
 */

@RestControllerAdvice(assignableTypes = {PublicCommentController.class, PrivateCommentController.class, AdminCommentController.class})
public class ErrorHandlerComment {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> notOwnerException(final RequestInvalidException e) {
        ApiError error = new ApiError(e.getMessage());
        error.setReason("For the requested operation the conditions are not met.");
        error.setStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiError> notFoundCommentException(final NotFoundCommentException e) {
        ApiError error = new ApiError(e.getMessage());
        error.setReason("The required object was not found.");
        error.setStatus(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> incorrectStateEventException(final IncorrectStateEventException e) {
        ApiError error = new ApiError(e.getMessage());
        error.setReason("For the requested operation the conditions are not met.");
        error.setStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
