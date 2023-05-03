package ru.practicum.mainService.error.exception;

public class RequestInvalidException extends RuntimeException {
    public RequestInvalidException(String message) {
        super(message);
    }
}
