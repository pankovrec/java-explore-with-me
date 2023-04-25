package ru.practicum.mainService.service.privates;

import ru.practicum.mainService.dto.request.ParticipationRequestDto;

import java.util.List;

/**
 * RequestServicePrivate
 */
public interface PrivateRequestService {

    /**
     * получить список запросов пользователя
     */
    List<ParticipationRequestDto> getUserRequests(Long userId);

    /**
     * добавить запрос на участие
     */
    ParticipationRequestDto postParticipationRequest(Long userId, Long eventId);

    /**
     * отменить запрос на участие
     */
    ParticipationRequestDto cancelRequest(Long userId, Long requestId);
}
