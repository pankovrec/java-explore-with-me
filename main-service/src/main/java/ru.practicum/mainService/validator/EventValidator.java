package ru.practicum.mainService.validator;

import org.springframework.stereotype.Component;
import ru.practicum.mainService.dto.event.NewEventDto;
import ru.practicum.mainService.dto.event.UpdateEventAdminRequest;
import ru.practicum.mainService.dto.event.UpdateEventUserRequest;
import ru.practicum.mainService.error.exception.EventIncorrectDateException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

/**
 * Event Validator
 */
@Component
public class EventValidator {

    public void validateEvent(NewEventDto eventDto) {

        if (eventDto.getEventDate() == null || eventDto.getEventDate().isBefore(LocalDateTime.now()
                .plus(2, ChronoUnit.HOURS))) {
            throw new EventIncorrectDateException("дата должна быть в прошлом " + eventDto.getEventDate());
        }
    }

    public void validateEvent(UpdateEventUserRequest eventDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ROOT);
        if (eventDto.getEventDate() != null && LocalDateTime.parse(eventDto.getEventDate(), formatter)
                .isBefore(LocalDateTime.now().plus(2, ChronoUnit.HOURS))) {
            throw new EventIncorrectDateException("дата должна быть в прошлом " + eventDto.getEventDate());
        }
    }

    public void validateEvent(UpdateEventAdminRequest eventDto) {
        if (eventDto.getEventDate() != null && eventDto.getEventDate()
                .isBefore(LocalDateTime.now().plus(2, ChronoUnit.HOURS))) {
            throw new EventIncorrectDateException("дата должна быть в прошлом " + eventDto.getEventDate());
        }
    }
}
