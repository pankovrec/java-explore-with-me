package ru.practicum.mainService.service.impl.admins;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.practicum.mainService.dto.event.EventFullDto;
import ru.practicum.mainService.dto.event.EventMapper;
import ru.practicum.mainService.dto.event.EventSpecifications;
import ru.practicum.mainService.dto.event.UpdateEventAdminRequest;
import ru.practicum.mainService.error.exception.EventIncorrectStateForAdmin;
import ru.practicum.mainService.model.Category;
import ru.practicum.mainService.model.Event;
import ru.practicum.mainService.model.State;
import ru.practicum.mainService.model.StateAction;
import ru.practicum.mainService.repository.admins.CategoryRepositoryAdmin;
import ru.practicum.mainService.repository.admins.EventRepositoryAdmin;
import ru.practicum.mainService.service.admins.EventServiceAdmin;
import ru.practicum.mainService.service.api.EventStatsService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * EventServiceAdminImpl
 */

@Service
public class EventServiceAdminImpl implements EventServiceAdmin {

    private final EventRepositoryAdmin repository;

    private final CategoryRepositoryAdmin categoryRepository;

    private final EventStatsService eventStatsService;

    @Autowired
    public EventServiceAdminImpl(EventRepositoryAdmin repository, CategoryRepositoryAdmin categoryRepository, EventStatsService eventStatsService) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.eventStatsService = eventStatsService;
    }

    @Override
    public List<EventFullDto> getEvents(List<Long> users, List<String> states, List<Long> categories, String rangeStart,
                                        String rangeEnd, Integer from, Integer size) {

        Specification<Event> spec = EventSpecifications.byUsers(users)
                .and(EventSpecifications.byStates(states))
                .and(EventSpecifications.byCategories(categories))
                .and(EventSpecifications.byRangeStart(rangeStart))
                .and(EventSpecifications.byRangeEnd(rangeEnd));

        Pageable pageRequest = PageRequest.of((from / size), size);

        List<Event> events = repository.findAll(spec, pageRequest).toList();
        Map<Long, Long> stats = eventStatsService.getStats(events, false);
        eventStatsService.setViews(stats, events);

        return events.stream().map(EventMapper::toFullEventDto).collect(Collectors.toList());
    }

    @Override
    public EventFullDto patchEvent(Long eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        Optional<Event> eventOptional = repository.findById(eventId);

        Event event = eventOptional.get();
        if ((updateEventAdminRequest.getStateAction() == StateAction.PUBLISH_EVENT)
                && event.getState() != State.PENDING) {
            throw new EventIncorrectStateForAdmin("Нельзя обновлять событие с таким статусом: "
                    + event.getState());
        }

        if (updateEventAdminRequest.getStateAction() == StateAction.REJECT_EVENT
                && event.getState() == State.PUBLISHED) {
            throw new EventIncorrectStateForAdmin("Нельзя обновлять событие с таким статусом: "
                    + event.getState());
        }

        if (updateEventAdminRequest.getAnnotation() != null)
            event.setAnnotation(updateEventAdminRequest.getAnnotation());
        if (updateEventAdminRequest.getCategory() != null) {
            Optional<Category> category = categoryRepository.findById(updateEventAdminRequest.getCategory());
            event.setCategory(category.get());
        }
        if (updateEventAdminRequest.getDescription() != null)
            event.setDescription(updateEventAdminRequest.getDescription());
        if (updateEventAdminRequest.getEventDate() != null)
            event.setEventDate(updateEventAdminRequest.getEventDate());
        if (updateEventAdminRequest.getLocation() != null) {
            event.setLat(updateEventAdminRequest.getLocation().getLat());
            event.setLon(updateEventAdminRequest.getLocation().getLon());
        }
        if (updateEventAdminRequest.getPaid() != null)
            event.setPaid(updateEventAdminRequest.getPaid());
        if (updateEventAdminRequest.getParticipantLimit() != null)
            event.setParticipantLimit(updateEventAdminRequest.getParticipantLimit());
        if (updateEventAdminRequest.getRequestModeration() != null)
            event.setRequestModeration(updateEventAdminRequest.getRequestModeration());
        if (updateEventAdminRequest.getStateAction() != null) {
            if (updateEventAdminRequest.getStateAction() == StateAction.PUBLISH_EVENT) {
                event.setState(State.PUBLISHED);
                event.setPublishedOn(LocalDateTime.now());
            } else if (updateEventAdminRequest.getStateAction() == StateAction.SEND_TO_REVIEW) {
                event.setState(State.PENDING);
            } else {
                event.setState(State.CANCELED);
            }
        }
        if (updateEventAdminRequest.getTitle() != null)
            event.setTitle(updateEventAdminRequest.getTitle());

        Event updatedEvent = repository.save(event);

        return EventMapper.toFullEventDto(updatedEvent);
    }
}
