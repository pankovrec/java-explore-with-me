package ru.practicum.mainService.service.impl.privates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
import java.util.Optional;
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
    public List<ParticipationRequestDto> getUserRequests(Long userId) {

        userService.getUser(userId);

        List<Request> requests = repository.findAllByRequesterId(userId);
        return requests.stream().map(RequestMapper::toRequestDto).collect(Collectors.toList());

    }

    @Override
    public ParticipationRequestDto postParticipationRequest(Long userId, Long eventId) {

        User user = userService.getUser(userId);

        Optional<Event> eventOptional = eventRepository.findById(eventId);

        Event event = eventOptional.get();
        if (event.getInitiator().getId().equals(userId)) {
            throw new RequestInvalidException("User cant request to his own event");
        }

        if (event.getState() != State.PUBLISHED) {
            throw new RequestInvalidException("Event not published");
        }

        if ((event.getParticipantLimit() <= event.getConfirmedRequests()) &&
                event.getParticipantLimit() > 0) {
            throw new RequestInvalidException("Event reached limit");
        }

        List<Request> requests = repository.findAllByRequesterIdAndEventId(userId, eventId);
        if (!requests.isEmpty()) {
            throw new ExistRequestException("Request already exist");
        }

        Request request = new Request();
        request.setRequester(user);
        request.setEvent(event);
        request.setCreated(LocalDateTime.now());
        request.setStatus(Status.PENDING);
        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            request.setStatus(Status.CONFIRMED);
            Long confirmedRequests = event.getConfirmedRequests();
            if (confirmedRequests == null)
                confirmedRequests = 0L;
            confirmedRequests++;
            event.setConfirmedRequests(confirmedRequests);
            eventRepository.save(event);
        }

        Request newRequest = repository.save(request);

        return RequestMapper.toRequestDto(newRequest);
    }

    @Override
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        userService.getUser(userId);

        Optional<Request> requestOptional = repository.findById(requestId);
        Request request = requestOptional.get();
        request.setStatus(Status.CANCELED);
        Request updatedRequest = repository.save(request);

        return RequestMapper.toRequestDto(updatedRequest);
    }
}
