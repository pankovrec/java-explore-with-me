package ru.practicum.mainService.error.exception;

public class UserIsNotAnInitiatorOfEventException extends RuntimeException {
    public UserIsNotAnInitiatorOfEventException(String message) {
        super(message);
    }
}
