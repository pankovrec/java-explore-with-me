package ru.practicum.mainService.error.exception;

public class ExistUserException extends RuntimeException {
    public ExistUserException(String message) {
        super(message);
    }
}
