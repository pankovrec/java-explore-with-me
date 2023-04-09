package ru.practicum.mainService.error.exception;

public class RequestParticipantLimitException extends RuntimeException {
    public RequestParticipantLimitException(String message) {
        super(message);
    }
}
