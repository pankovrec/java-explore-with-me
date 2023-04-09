package ru.practicum.mainService.error.exception;

public class EventIncorrectStateForAdmin extends RuntimeException {
    public EventIncorrectStateForAdmin(String message) {
        super(message);
    }
}
