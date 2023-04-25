package ru.practicum.mainService.error.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.mainService.controller.admins.AdminUserController;
import ru.practicum.mainService.error.ApiError;
import ru.practicum.mainService.error.exception.ExistUserException;
import ru.practicum.mainService.service.impl.publics.PublicUserServiceImpl;

@RestControllerAdvice(assignableTypes = {PublicUserServiceImpl.class, AdminUserController.class})
public class ErrorUserHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ApiError> alreadyExistUserException(final ExistUserException e) {
        ApiError error = new ApiError(e.getMessage());
        error.setReason("Incorrectly made request.");
        error.setStatus(HttpStatus.CONFLICT);
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}
