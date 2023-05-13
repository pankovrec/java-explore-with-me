package ru.practicum.mainService.service.impl.privates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.mainService.dto.event.*;
import ru.practicum.mainService.dto.request.ParticipationRequestDto;
import ru.practicum.mainService.dto.request.RequestMapper;
import ru.practicum.mainService.error.exception.IncorrectStateEventException;
import ru.practicum.mainService.error.exception.LimitRequestParticipantException;
import ru.practicum.mainService.error.exception.NotFoundCategoryException;
import ru.practicum.mainService.error.exception.NotFoundEventException;
import ru.practicum.mainService.model.*;
import ru.practicum.mainService.repository.privates.PrivateEventRepository;
import ru.practicum.mainService.repository.publics.PublicCategoryRepository;
import ru.practicum.mainService.repository.publics.PublicRequestRepository;
import ru.practicum.mainService.service.privates.PrivateEventService;
import ru.practicum.mainService.service.publics.PublicUserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * EventServicePrivateImpl
 */

@Service
public class PrivateEventServiceImpl implements PrivateEventService {

    private final PrivateEventRepository repository;

    private final PublicCategoryRepository categoryRepository;

    private final PublicRequestRepository requestRepository;

    private final PublicUserService userService;

    @Autowired
    public PrivateEventServiceImpl(PrivateEventRepository repository, PublicCategoryRepository categoryRepository,
                                   PublicRequestRepository requestRepository, PublicUserService userService) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.requestRepository = requestRepository;
        this.userService = userService;
    }

    @Override
    public List<EventShortDto> getEvents(Long userId, Integer from, Integer size) {
        Pageable pageRequest = PageRequest.of((from / size), size);
        List<Event> events = repository.findAllByInitiatorId(userId, pageRequest);
        return events.stream().map(EventMapper::toShortEventDto).collect(Collectors.toList());
    }

    @Override
    public EventFullDto getEvent(Long userId, Long eventId) {
        Event event = repository.findByIdAndInitiatorId(eventId, userId);
        return EventMapper.toFullEventDto(event);
    }

    @Override
    public List<ParticipationRequestDto> getParticipants(Long userId, Long eventId) {
        return requestRepository.findAllByEventId(eventId).stream().map(RequestMapper::toRequestDto).collect(Collectors.toList());
    }

    @Override
    public EventFullDto postEvent(Long userId, NewEventDto eventDto) {

        Event event = EventMapper.fromEventDto(eventDto);
        event.setState(State.PENDING);
        event.setCategory(categoryRepository.findById(eventDto.getCategory()).orElseThrow(() -> new NotFoundCategoryException(String.format("Category =%s not found", eventDto.getCategory()))));
        event.setInitiator(userService.getUser(userId));
        event.setCreatedOn(LocalDateTime.now());
        event.setConfirmedRequests(0L);

        return EventMapper.toFullEventDto(repository.save(event));
    }

    @Override
    public EventFullDto patchEvent(Long userId, Long eventId, UpdateUserRequest eventDto) {

        Event event = repository.findById(eventId).orElseThrow(() -> new NotFoundEventException(String.format("Event id=%s not found", eventId)));
        checkStatus(eventDto, event);
        Event updatedEvent = repository.save(event);

        return EventMapper.toFullEventDto(updatedEvent);
    }

    @Override
    public RequestStatusUpdateResult changeStatusOfRequest(Long userId,
                                                           Long eventId,
                                                           RequestStatusUpdateRequest requestStatusUpdateRequest) {

        Status newStatus = requestStatusUpdateRequest.getStatus();

        userService.getUser(userId);

        Event event = repository.findById(eventId).orElseThrow(() -> new NotFoundEventException(String.format("Event id=%s not found", eventId)));

        Integer limit = event.getParticipantLimit();
        Long confirmedRequests = event.getConfirmedRequests();

        if ((event.getParticipantLimit() > 0 && event.getRequestModeration())
                && limit <= confirmedRequests) {
            throw new LimitRequestParticipantException("Лимит участников исчерпан.");
        }

        List<Request> requests = requestRepository.findAllByIdIn(requestStatusUpdateRequest.getRequestIds());
        List<RequestStatusUpdateResult.ParticipationRequestDto> confirmed = new ArrayList<>();
        List<RequestStatusUpdateResult.ParticipationRequestDto> rejected = new ArrayList<>();

        for (Request request : requests) {

            request.setStatus(newStatus);

            switch (newStatus) {
                case CONFIRMED:
                    confirmed.add(RequestStatusUpdateResult.ParticipationRequestDto.toParticipationRequestDto(request));
                    confirmedRequests++;
                    event.setConfirmedRequests(confirmedRequests);
                    limit++;
                    break;
                case REJECTED:
                    rejected.add(RequestStatusUpdateResult.ParticipationRequestDto.toParticipationRequestDto(request));
                    break;
            }
        }

        requestRepository.saveAll(requests);

        RequestStatusUpdateResult result = new RequestStatusUpdateResult();
        result.setConfirmedRequests(confirmed);
        result.setRejectedRequests(rejected);
        return result;
    }

    private static void checkStatus(UpdateUserRequest eventDto, Event event) {
        if (event.getState() != State.CANCELED && event.getState() != State.PENDING) {
            throw new IncorrectStateEventException("Событие с таким статусом не может быть опубликовано.");
        }

        if (eventDto.getStateAction() != null)
            event.setState(eventDto.getStateAction() == StateAction.REJECT_EVENT ||
                    eventDto.getStateAction() == StateAction.CANCEL_REVIEW ? State.CANCELED : State.PENDING);
    }
}
