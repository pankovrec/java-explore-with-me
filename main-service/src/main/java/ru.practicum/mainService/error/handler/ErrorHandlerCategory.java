package ru.practicum.mainService.error.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.mainService.controller.admins.AdminCategoryController;
import ru.practicum.mainService.controller.publics.PublicCategoryController;
import ru.practicum.mainService.error.ApiError;
import ru.practicum.mainService.error.exception.DuplicateNameCategoryException;
import ru.practicum.mainService.error.exception.NotEmptyCategoryException;
import ru.practicum.mainService.error.exception.NotFoundCategoryException;

@RestControllerAdvice(assignableTypes = {AdminCategoryController.class, PublicCategoryController.class})
public class ErrorHandlerCategory {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiError> notFoundCategoryException(final NotFoundCategoryException e) {
        ApiError error = new ApiError(e.getMessage());
        error.setReason("The required object was not found.");
        error.setStatus(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ApiError> duplicateCategoryNameException(final DuplicateNameCategoryException e) {
        ApiError error = new ApiError(e.getMessage());
        error.setReason("Integrity constraint has been violated.");
        error.setStatus(HttpStatus.CONFLICT);
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ApiError> categoryNotEmptyException(final NotEmptyCategoryException e) {
        ApiError error = new ApiError(e.getMessage());
        error.setReason("For the requested operation the conditions are not met.");
        error.setStatus(HttpStatus.CONFLICT);
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}
