package ru.practicum.mainService.error.exception;

public class NotEmptyCategoryException extends RuntimeException {
    public NotEmptyCategoryException(String message) {
        super(message);
    }
}
