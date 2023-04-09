package ru.practicum.mainService.error.exception;

public class CategoryNameDuplicateException extends RuntimeException {
    public CategoryNameDuplicateException(String message) {
        super(message);
    }
}
