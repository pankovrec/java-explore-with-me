package ru.practicum.mainService.error.exception;

public class IncorrectStateEventException extends RuntimeException {
    public IncorrectStateEventException(String message) {
        super(message);
    }
}
