package ru.practicum.mainService.service.api;

import ru.practicum.mainService.model.Event;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * EventStatsService
 */
public interface EventStatsService {

    /**
     * Получить статистику
     */
    Map<Long, Long> getStats(List<Event> events, Boolean unique);

    /**
     * добавить в статистику
     */
    void postStats(HttpServletRequest request);

    /**
     * добавить просмотры
     */
    void postViews(Map<Long, Long> views, List<Event> events);
}