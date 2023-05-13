package ru.practicum.mainService.error.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.mainService.controller.admins.AdminUserController;
import ru.practicum.mainService.controller.privates.PrivateCommentController;
import ru.practicum.mainService.error.ApiError;
import ru.practicum.mainService.error.exception.ExistUserException;
import ru.practicum.mainService.error.exception.NotFoundUserException;
import ru.practicum.mainService.service.impl.publics.PublicUserServiceImpl;

/**
 * Error User Handler.
 */

@RestControllerAdvice(assignableTypes = {PublicUserServiceImpl.class, AdminUserController.class, PrivateCommentController.class})
public class ErrorUserHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ApiError> alreadyExistUserException(final ExistUserException e) {
        ApiError error = new ApiError(e.getMessage());
        error.setReason("Incorrectly made request.");
        error.setStatus(HttpStatus.CONFLICT);
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiError> notFoundUserException(final NotFoundUserException e) {
        ApiError error = new ApiError(e.getMessage());
        error.setReason("The required object was not found.");
        error.setStatus(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
