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
import ru.practicum.mainService.model.*;
import ru.practicum.mainService.repository.privates.PrivateEventRepository;
import ru.practicum.mainService.repository.publics.PublicCategoryRepository;
import ru.practicum.mainService.repository.publics.PublicRequestRepository;
import ru.practicum.mainService.service.privates.PrivateEventService;
import ru.practicum.mainService.service.publics.PublicUserService;
import ru.practicum.mainService.service.stats.StatsEventService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    private final StatsEventService statsEventService;

    @Autowired
    public PrivateEventServiceImpl(PrivateEventRepository repository, PublicCategoryRepository categoryRepository,
                                   PublicRequestRepository requestRepository, PublicUserService userService,
                                   StatsEventService statsEventService) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.requestRepository = requestRepository;
        this.userService = userService;
        this.statsEventService = statsEventService;
    }

    @Override
    public List<EventShortDto> getEvents(Long userId, Integer from, Integer size) {
        Pageable pageRequest = PageRequest.of((from / size), size);
        List<Event> events = repository.findAllByInitiatorId(userId, pageRequest);
        Map<Long, Long> stats = statsEventService.getStats(events, false);
        statsEventService.postViews(stats, events);
        return events.stream().map(EventMapper::toShortEventDto).collect(Collectors.toList());
    }

    @Override
    public EventFullDto getEventByEventId(Long userId, Long eventId) {
        Event event = repository.findByIdAndInitiatorId(eventId, userId);
        Map<Long, Long> stats = statsEventService.getStats(List.of(event), false);
        event.setViews(stats.get(eventId));
        return EventMapper.toFullEventDto(event);
    }

    @Override
    public List<ParticipationRequestDto> getEventParticipants(Long userId, Long eventId) {

        List<Request> requests = requestRepository.findAllByEventId(eventId);
        return requests.stream().map(RequestMapper::toRequestDto).collect(Collectors.toList());
    }

    @Override
    public EventFullDto postEvent(Long userId, NewEventDto eventDto) {
        Optional<Category> category = categoryRepository.findById(eventDto.getCategory());

        User user = userService.getUser(userId);

        Event event = EventMapper.fromEventDto(eventDto);
        event.setState(State.PENDING);
        event.setCategory(category.get());
        event.setInitiator(user);
        event.setCreatedOn(LocalDateTime.now());
        event.setConfirmedRequests(0L);
        Event newEvent = repository.save(event);
        newEvent.setViews(0L);

        return EventMapper.toFullEventDto(newEvent);
    }

    @Override
    public EventFullDto patchUserEventById(Long userId, Long eventId, UpdateUserRequest eventDto) {

        Optional<Event> eventOptional = repository.findById(eventId);
        Event event = eventOptional.get();

        if (event.getState() != State.CANCELED && event.getState() != State.PENDING) {
            throw new IncorrectStateEventException("Событие с таким статусом не может быть опубликовано.");
        }

        if (eventDto.getStateAction() != null)
            if (eventDto.getStateAction() == StateAction.REJECT_EVENT ||
                    eventDto.getStateAction() == StateAction.CANCEL_REVIEW) {
                event.setState(State.CANCELED);
            } else {
                event.setState(State.PENDING);
            }

        Event updatedEvent = repository.save(event);
        Map<Long, Long> stats = statsEventService.getStats(List.of(updatedEvent), false);
        updatedEvent.setViews(stats.get(eventId));

        return EventMapper.toFullEventDto(updatedEvent);
    }

    @Override
    public RequestStatusUpdateResult changeRequestStatus(Long userId,
                                                         Long eventId,
                                                         RequestStatusUpdateRequest requestStatusUpdateRequest) {

        RequestStatusUpdateResult result = new RequestStatusUpdateResult();
        List<RequestStatusUpdateResult.ParticipationRequestDto> confirmedRequests = new ArrayList<>();
        List<RequestStatusUpdateResult.ParticipationRequestDto> rejectedRequests = new ArrayList<>();

        Status newStatus = requestStatusUpdateRequest.getStatus();

        userService.getUser(userId);
        Optional<Event> eventOptional = repository.findById(eventId);

        Event event = eventOptional.get();

        Integer limit = event.getParticipantLimit();
        Long confirmed = event.getConfirmedRequests();

        if ((event.getParticipantLimit() > 0 && event.getRequestModeration())
                && limit <= confirmed) {
            throw new LimitRequestParticipantException("Лимит участников исчерпан.");
        }

        List<Request> requests = requestRepository
                .findAllByIdIn(requestStatusUpdateRequest.getRequestIds());

        for (Request request : requests) {

            request.setStatus(newStatus);

            if (newStatus == Status.CONFIRMED) {
                confirmedRequests.add(RequestStatusUpdateResult.ParticipationRequestDto.toParticipationRequestDto(request));
                confirmed++;
                event.setConfirmedRequests(confirmed);
                limit++;
            }
            if (newStatus == Status.REJECTED)
                rejectedRequests.add(RequestStatusUpdateResult.ParticipationRequestDto.toParticipationRequestDto(request));
        }

        requestRepository.saveAll(requests);

        result.setConfirmedRequests(confirmedRequests);
        result.setRejectedRequests(rejectedRequests);
        return result;
    }
}
