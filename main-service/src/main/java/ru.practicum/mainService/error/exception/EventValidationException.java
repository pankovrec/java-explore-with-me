package ru.practicum.mainService.error.exception;

public class EventValidationException extends RuntimeException {
    public EventValidationException(String message) {
        super(message);
    }
}
