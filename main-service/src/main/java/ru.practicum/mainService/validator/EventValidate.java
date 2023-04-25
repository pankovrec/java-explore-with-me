package ru.practicum.mainService.validator;

import org.springframework.stereotype.Component;
import ru.practicum.mainService.dto.event.NewEventDto;
import ru.practicum.mainService.dto.event.UpdateAdminRequest;
import ru.practicum.mainService.dto.event.UpdateUserRequest;
import ru.practicum.mainService.error.exception.EventDateIncorrectException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

/**
 * Event Validator
 */
@Component
public class EventValidate {

    public void eventValidateDate(NewEventDto eventDto) {

        if (eventDto.getEventDate() == null || eventDto.getEventDate().isBefore(LocalDateTime.now()
                .plus(2, ChronoUnit.HOURS))) {
            throw new EventDateIncorrectException("дата должна быть в прошлом " + eventDto.getEventDate());
        }
    }

    public void eventValidateDate(UpdateUserRequest eventDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ROOT);
        if (eventDto.getEventDate() != null && LocalDateTime.parse(eventDto.getEventDate(), formatter)
                .isBefore(LocalDateTime.now().plus(2, ChronoUnit.HOURS))) {
            throw new EventDateIncorrectException("дата должна быть в прошлом " + eventDto.getEventDate());
        }
    }

    public void eventValidateDate(UpdateAdminRequest eventDto) {
        if (eventDto.getEventDate() != null && eventDto.getEventDate()
                .isBefore(LocalDateTime.now().plus(2, ChronoUnit.HOURS))) {
            throw new EventDateIncorrectException("дата должна быть в прошлом " + eventDto.getEventDate());
        }
    }
}