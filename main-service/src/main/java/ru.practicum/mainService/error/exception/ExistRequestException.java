package ru.practicum.mainService.error.exception;

public class ExistRequestException extends RuntimeException {
    public ExistRequestException(String message) {
        super(message);
    }
}
