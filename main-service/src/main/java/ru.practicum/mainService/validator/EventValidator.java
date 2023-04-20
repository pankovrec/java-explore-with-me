package ru.practicum.mainService.validator;

import org.springframework.stereotype.Component;
import ru.practicum.mainService.dto.event.NewEventDto;
import ru.practicum.mainService.dto.event.UpdateEventAdminRequest;
import ru.practicum.mainService.dto.event.UpdateEventUserRequest;
import ru.practicum.mainService.error.exception.EventIncorrectDateException;
import ru.practicum.mainService.error.exception.EventValidationException;

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

        if (eventDto.getAnnotation() == null) {
            throw new EventValidationException("Field: annotation. Error: Поле должно быть заполнено. " +
                    "Value: " + eventDto.getAnnotation());
        }

        if (eventDto.getCategory() == null) {
            throw new EventValidationException("Field: category. Error: Поле должно быть заполнено. " +
                    "Value: " + eventDto.getCategory());
        }

        if (eventDto.getLocation() == null) {
            throw new EventValidationException("Field: location. Error: Поле должно быть заполнено. " +
                    "Value: " + eventDto.getLocation());
        }

        if (eventDto.getTitle() == null) {
            throw new EventValidationException("Field: title. Error: Поле должно быть заполнено. " +
                    "Value: " + eventDto.getTitle());
        }

        if (eventDto.getEventDate() == null || eventDto.getEventDate().isBefore(LocalDateTime.now()
                .plus(2, ChronoUnit.HOURS))) {
            throw new EventIncorrectDateException("Field: eventDate. Error: должно содержать дату, " +
                    "которая еще не наступила. Value: " + eventDto.getEventDate());
        }

        if (eventDto.getParticipantLimit() != null && eventDto.getParticipantLimit() < 0) {
            throw new EventValidationException("Field: participantLimit. Error: должно содержать положительное число. " +
                    "Value: " + eventDto.getParticipantLimit());
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
