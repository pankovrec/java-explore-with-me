package ru.practicum.mainService.error.exception;

public class IncorrectRequestStatusException extends RuntimeException {
    public IncorrectRequestStatusException(String message) {
        super(message);
    }
}
