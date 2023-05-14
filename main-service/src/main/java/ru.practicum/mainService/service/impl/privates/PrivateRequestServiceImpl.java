package ru.practicum.mainService.service.impl.privates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainService.dto.request.ParticipationRequestDto;
import ru.practicum.mainService.dto.request.RequestMapper;
import ru.practicum.mainService.error.exception.ExistRequestException;
import ru.practicum.mainService.error.exception.RequestInvalidException;
import ru.practicum.mainService.model.*;
import ru.practicum.mainService.repository.privates.PrivateEventRepository;
import ru.practicum.mainService.repository.privates.PrivateRequestRepository;
import ru.practicum.mainService.service.privates.PrivateRequestService;
import ru.practicum.mainService.service.publics.PublicUserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * RequestServicePrivateImpl
 */

@Service
public class PrivateRequestServiceImpl implements PrivateRequestService {

    private final PrivateRequestRepository repository;

    private final PublicUserService userService;

    private final PrivateEventRepository eventRepository;

    @Autowired
    public PrivateRequestServiceImpl(PrivateRequestRepository repository, PublicUserService userService,
                                     PrivateEventRepository eventRepository) {
        this.repository = repository;
        this.userService = userService;
        this.eventRepository = eventRepository;
    }

    @Override
    public List<ParticipationRequestDto> getRequests(Long userId) {

        userService.getUser(userId);

        List<Request> requests = repository.findAllByRequesterId(userId);
        return requests.stream().map(RequestMapper::toRequestDto).collect(Collectors.toList());

    }

    @Override
    @Transactional
    public ParticipationRequestDto postRequest(Long userId, Long eventId) {

        User user = userService.getUser(userId);

        Event event = eventRepository.findById(eventId).orElseThrow(NoSuchElementException::new);
        check(userId, eventId, event);

        Request request = new Request();
        request.setRequester(user);
        request.setEvent(event);
        request.setCreated(LocalDateTime.now());
        request.setStatus(Status.PENDING);
        if (event.getRequestModeration() && event.getParticipantLimit() != 0) {
            return RequestMapper.toRequestDto(repository.save(request));
        }
        request.setStatus(Status.CONFIRMED);
        Long confirmedRequests = event.getConfirmedRequests();
        confirmedRequests++;
        event.setConfirmedRequests(confirmedRequests);
        eventRepository.save(event);

        return RequestMapper.toRequestDto(repository.save(request));
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        userService.getUser(userId);
        Request request = repository.findById(requestId).orElseThrow(NoSuchElementException::new);
        request.setStatus(Status.CANCELED);

        return RequestMapper.toRequestDto(repository.save(request));
    }

    private void check(Long userId, Long eventId, Event event) {
        if (event.getInitiator().getId().equals(userId)) {
            throw new RequestInvalidException("Запрос не может оставить создатель");
        }

        if (event.getState() != State.PUBLISHED) {
            throw new RequestInvalidException("Статус должен быть PUBLISHED");
        }

        if ((event.getParticipantLimit() <= event.getConfirmedRequests()) &&
                event.getParticipantLimit() > 0) {
            throw new RequestInvalidException("Исчерпан лимит участников");
        }

        List<Request> requests = repository.findAllByRequesterIdAndEventId(userId, eventId);
        if (!requests.isEmpty()) {
            throw new ExistRequestException("Такая запись уже есть");
        }
    }
}
