package ru.practicum.mainService.error.exception;

public class EventDateIncorrectException extends RuntimeException {
    public EventDateIncorrectException(String message) {
        super(message);
    }
}
