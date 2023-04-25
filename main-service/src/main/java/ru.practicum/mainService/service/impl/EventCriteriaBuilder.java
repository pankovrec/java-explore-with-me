package ru.practicum.mainService.service.impl;

import org.springframework.data.jpa.domain.Specification;
import ru.practicum.mainService.model.Event;
import ru.practicum.mainService.model.State;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * EventCriteriaFilterBuilder
 */

public class EventCriteriaBuilder {

    public static Specification<Event> byUsers(List<Long> users) {
        return (root, query, cb) -> users == null ? null : cb.in(root.get("initiator").get("id")).value(users);
    }

    public static Specification<Event> byStates(List<String> states) {
        return (root, query, cb) -> states == null ? null : cb.in(root.get("state")).value(states.stream().map(
                State::valueOf).collect(Collectors.toList()));
    }

    public static Specification<Event> byCategories(List<Long> categories) {
        return (root, query, cb) -> categories == null ? null : cb.in(root.get("category").get("id")).value(categories);
    }

    public static Specification<Event> byStart(String start) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ROOT);
        return (root, query, cb) -> start == null ? null : cb.greaterThan(root.get("eventDate"),
                LocalDateTime.parse(start, formatter));
    }

    public static Specification<Event> byEnd(String end) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ROOT);
        return (root, query, cb) -> end == null ? null : cb.lessThan(root.get("eventDate"),
                LocalDateTime.parse(end, formatter));
    }

    public static Specification<Event> byText(String text) {
        return byAnnotation(text).or(byDescription(text)).or(byTitle(text));
    }

    public static Specification<Event> byAnnotation(String text) {
        return (root, query, cb) -> text == null ? null : cb.like(cb.upper(root.get("annotation")), "%" + text.toUpperCase() + "%");
    }

    public static Specification<Event> byDescription(String text) {
        return (root, query, cb) -> text == null ? null : cb.like(cb.upper(root.get("description")), "%" + text.toUpperCase() + "%");
    }

    public static Specification<Event> byTitle(String text) {
        return (root, query, cb) -> text == null ? null : cb.like(cb.upper(root.get("title")), "%" + text.toUpperCase() + "%");
    }

    public static Specification<Event> byPaid(Boolean paid) {
        return (root, query, cb) -> paid == null ? null : cb.equal(root.get("paid"), paid);
    }

    public static Specification<Event> byAvailable(Boolean available) {
        return (root, query, cb) -> (available == null || !available) ? null :
                cb.lessThanOrEqualTo(root.get("participantLimit"), root.get("confirmedRequests"));
    }
}
