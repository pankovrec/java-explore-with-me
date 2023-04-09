package ru.practicum.mainService.error.exception;

public class EventIncorrectState extends RuntimeException {
    public EventIncorrectState(String message) {
        super(message);
    }
}
