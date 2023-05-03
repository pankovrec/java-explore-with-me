package ru.practicum.mainService.error.exception;

public class DuplicateNameCategoryException extends RuntimeException {
    public DuplicateNameCategoryException(String message) {
        super(message);
    }
}
