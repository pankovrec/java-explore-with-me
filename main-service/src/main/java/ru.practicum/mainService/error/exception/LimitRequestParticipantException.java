package ru.practicum.mainService.error.exception;

public class LimitRequestParticipantException extends RuntimeException {
    public LimitRequestParticipantException(String message) {
        super(message);
    }
}
