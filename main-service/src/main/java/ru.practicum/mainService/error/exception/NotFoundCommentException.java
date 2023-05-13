package ru.practicum.mainService.error.exception;

public class NotFoundCommentException extends RuntimeException {
    public NotFoundCommentException(String message) {
        super(message);
    }
}
