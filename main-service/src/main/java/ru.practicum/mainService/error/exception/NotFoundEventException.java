package ru.practicum.mainService.error.exception;

public class NotFoundEventException extends RuntimeException {
    public NotFoundEventException(String message) {
        super(message);
    }
}
