package ru.practicum.mainService.error.exception;

public class CategoryIsNotEmptyException extends RuntimeException {
    public CategoryIsNotEmptyException(String message) {
        super(message);
    }
}
