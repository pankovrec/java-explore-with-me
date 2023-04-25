package ru.practicum.mainService.service.impl.admins;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.practicum.mainService.dto.event.EventFullDto;
import ru.practicum.mainService.dto.event.EventMapper;
import ru.practicum.mainService.dto.event.UpdateAdminRequest;
import ru.practicum.mainService.error.exception.IncorrectStateEventAdminException;
import ru.practicum.mainService.model.Category;
import ru.practicum.mainService.model.Event;
import ru.practicum.mainService.model.State;
import ru.practicum.mainService.model.StateAction;
import ru.practicum.mainService.repository.admins.AdminCategoryRepository;
import ru.practicum.mainService.repository.admins.AdminEventRepository;
import ru.practicum.mainService.service.admins.AdminEventService;
import ru.practicum.mainService.service.impl.EventCriteriaBuilder;
import ru.practicum.mainService.service.stats.StatsEventService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * EventServiceAdminImpl
 */

@Service
public class AdminEventServiceImpl implements AdminEventService {

    private final AdminEventRepository repository;

    private final AdminCategoryRepository categoryRepository;

    private final StatsEventService statsEventService;

    @Autowired
    public AdminEventServiceImpl(AdminEventRepository repository, AdminCategoryRepository categoryRepository, StatsEventService statsEventService) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.statsEventService = statsEventService;
    }

    @Override
    public List<EventFullDto> getEvents(List<Long> users, List<String> states, List<Long> categories, String rangeStart,
                                        String rangeEnd, Integer from, Integer size) {

        Specification<Event> filter = EventCriteriaBuilder.byUsers(users)
                .and(EventCriteriaBuilder.byStates(states))
                .and(EventCriteriaBuilder.byCategories(categories))
                .and(EventCriteriaBuilder.byStart(rangeStart))
                .and(EventCriteriaBuilder.byEnd(rangeEnd));

        Pageable pageRequest = PageRequest.of((from / size), size);

        List<Event> events = repository.findAll(filter, pageRequest).toList();
        Map<Long, Long> stats = statsEventService.getStats(events, false);
        statsEventService.postViews(stats, events);

        return events.stream().map(EventMapper::toFullEventDto).collect(Collectors.toList());
    }

    @Override
    public EventFullDto patchEvent(Long eventId, UpdateAdminRequest updateAdminRequest) {
        Optional<Event> eventOptional = repository.findById(eventId);

        Event event = eventOptional.get();
        checkStatus(updateAdminRequest, event);

        if (updateAdminRequest.getAnnotation() != null)
            event.setAnnotation(updateAdminRequest.getAnnotation());
        if (updateAdminRequest.getCategory() != null) {
            Optional<Category> category = categoryRepository.findById(updateAdminRequest.getCategory());
            event.setCategory(category.get());
        }
        if (updateAdminRequest.getDescription() != null)
            event.setDescription(updateAdminRequest.getDescription());
        if (updateAdminRequest.getEventDate() != null)
            event.setEventDate(updateAdminRequest.getEventDate());
        if (updateAdminRequest.getLocation() != null) {
            event.setLat(updateAdminRequest.getLocation().getLat());
            event.setLon(updateAdminRequest.getLocation().getLon());
        }
        if (updateAdminRequest.getPaid() != null)
            event.setPaid(updateAdminRequest.getPaid());
        if (updateAdminRequest.getParticipantLimit() != null)
            event.setParticipantLimit(updateAdminRequest.getParticipantLimit());
        if (updateAdminRequest.getRequestModeration() != null)
            event.setRequestModeration(updateAdminRequest.getRequestModeration());
        if (updateAdminRequest.getStateAction() != null) {
            if (updateAdminRequest.getStateAction() == StateAction.PUBLISH_EVENT) {
                event.setState(State.PUBLISHED);
                event.setPublishedOn(LocalDateTime.now());
            } else if (updateAdminRequest.getStateAction() == StateAction.SEND_TO_REVIEW) {
                event.setState(State.PENDING);
            } else {
                event.setState(State.CANCELED);
            }
        }
        if (updateAdminRequest.getTitle() != null)
            event.setTitle(updateAdminRequest.getTitle());

        Event prePatchedEvent = repository.save(event);

        return EventMapper.toFullEventDto(prePatchedEvent);
    }

    private static void checkStatus(UpdateAdminRequest updateAdminRequest, Event event) {
        if ((updateAdminRequest.getStateAction() == StateAction.PUBLISH_EVENT)
                && event.getState() != State.PENDING) {
            throw new IncorrectStateEventAdminException("Нельзя обновлять событие с таким статусом: "
                    + event.getState());
        }

        if (updateAdminRequest.getStateAction() == StateAction.REJECT_EVENT
                && event.getState() == State.PUBLISHED) {
            throw new IncorrectStateEventAdminException("Нельзя обновлять событие с таким статусом: "
                    + event.getState());
        }
    }
}
