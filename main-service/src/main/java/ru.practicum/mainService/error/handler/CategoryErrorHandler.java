package ru.practicum.mainService.error.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.mainService.controller.admins.CategoryControllerAdmin;
import ru.practicum.mainService.controller.publics.CategoryControllerPublic;
import ru.practicum.mainService.error.ApiError;
import ru.practicum.mainService.error.exception.CategoryIsNotEmptyException;
import ru.practicum.mainService.error.exception.CategoryNotFoundException;
import ru.practicum.mainService.error.exception.CategoryNameDuplicateException;

@RestControllerAdvice(assignableTypes = {CategoryControllerAdmin.class, CategoryControllerPublic.class})
public class CategoryErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiError> handleCategoryNotFoundException(final CategoryNotFoundException e) {
        ApiError error = new ApiError(e.getMessage());
        error.setReason("The required object was not found.");
        error.setStatus(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ApiError> handleCategoryWithNameExistException(final CategoryNameDuplicateException e) {
        ApiError error = new ApiError(e.getMessage());
        error.setReason("Integrity constraint has been violated.");
        error.setStatus(HttpStatus.CONFLICT);
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ApiError> handleCategoryIsNotEmptyException(final CategoryIsNotEmptyException e) {
        ApiError error = new ApiError(e.getMessage());
        error.setReason("For the requested operation the conditions are not met.");
        error.setStatus(HttpStatus.CONFLICT);
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}
