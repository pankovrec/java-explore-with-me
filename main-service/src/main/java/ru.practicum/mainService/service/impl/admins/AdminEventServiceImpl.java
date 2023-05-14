package ru.practicum.mainService.service.impl.admins;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainService.dto.event.EventFullDto;
import ru.practicum.mainService.dto.event.EventMapper;
import ru.practicum.mainService.dto.event.UpdateAdminRequest;
import ru.practicum.mainService.error.exception.IncorrectStateEventAdminException;
import ru.practicum.mainService.model.Event;
import ru.practicum.mainService.model.QEvent;
import ru.practicum.mainService.model.State;
import ru.practicum.mainService.model.StateAction;
import ru.practicum.mainService.repository.admins.AdminCategoryRepository;
import ru.practicum.mainService.repository.admins.AdminEventRepository;
import ru.practicum.mainService.service.admins.AdminEventService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * EventServiceAdminImpl
 */

@Service
public class AdminEventServiceImpl implements AdminEventService {

    private final AdminEventRepository repository;

    private final AdminCategoryRepository categoryRepository;

    @Autowired
    public AdminEventServiceImpl(AdminEventRepository repository, AdminCategoryRepository categoryRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<EventFullDto> getEvents(List<Long> users, List<String> states, List<Long> categories, String rangeStart,
                                        String rangeEnd, Integer from, Integer size) {

        QEvent qEvent = QEvent.event;
        BooleanExpression filter = qEvent.isNotNull();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ROOT);
        if (!users.isEmpty()) {
            filter = filter.and(qEvent.initiator.id.in(users));
        }
        if (states != null) {
            filter = filter.and(qEvent.state.in(states.stream().map(
                    State::valueOf).collect(Collectors.toList())));
        }
        if (categories != null) {
            filter = filter.and(qEvent.category.id.in(categories));
        }
        if (rangeEnd != null && rangeStart != null) {
            filter = filter.and(qEvent.eventDate.between(LocalDateTime.parse(rangeStart, formatter), LocalDateTime.parse(rangeEnd, formatter)));
        }
        Pageable pageRequest = PageRequest.of((from / size), size);
        List<Event> filteredEvents = repository.findAll(filter, pageRequest).toList();
        return filteredEvents.stream().map(EventMapper::toFullEventDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventFullDto patchEvent(Long eventId, UpdateAdminRequest updateAdminRequest) {

        Event event = repository.findById(eventId).orElseThrow(NoSuchElementException::new);
        checkStatus(updateAdminRequest, event);

        if (updateAdminRequest.getAnnotation() != null)
            event.setAnnotation(updateAdminRequest.getAnnotation());
        if (updateAdminRequest.getCategory() != null) {
            event.setCategory(categoryRepository.findById(updateAdminRequest.getCategory()).orElseThrow());
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
        if (updateAdminRequest.getTitle() != null)
            event.setTitle(updateAdminRequest.getTitle());
        if (updateAdminRequest.getStateAction() != null) {
            switch (updateAdminRequest.getStateAction()) {
                case PUBLISH_EVENT:
                    event.setState(State.PUBLISHED);
                    event.setPublishedOn(LocalDateTime.now());
                    break;
                case SEND_TO_REVIEW:
                    event.setState(State.PENDING);
                    break;
                default:
                    event.setState(State.CANCELED);
                    break;
            }
        }
        return EventMapper.toFullEventDto(repository.save(event));
    }

    private static void checkStatus(UpdateAdminRequest updateAdminRequest, Event event) {
        if (updateAdminRequest.getStateAction() == StateAction.REJECT_EVENT
                && event.getState() == State.PUBLISHED || updateAdminRequest.getStateAction() == StateAction.PUBLISH_EVENT
                && event.getState() != State.PENDING) {
            throw new IncorrectStateEventAdminException("Нельзя обновить событие с таким статусом: "
                    + event.getState());
        }
    }
}
