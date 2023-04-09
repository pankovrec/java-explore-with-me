package ru.practicum.mainService.error.exception;

public class EventIncorrectDateException extends RuntimeException {
    public EventIncorrectDateException(String message) {
        super(message);
    }
}
