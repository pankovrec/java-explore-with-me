package ru.practicum.exploreWithMe.service;

import ru.practicum.exploreWithMe.model.Hit;
import ru.practicum.exploreWithMe.model.ViewStats;

import java.util.List;

/**
 * Stats service
 */
public interface StatsService {

    /**
     * добавить hit
     */
    Hit saveHit(Hit hit);

    /**
     * просмотр статистику
     */
    List<ViewStats> viewStats(String start, String end, List<String> uris, Boolean unique);
}
