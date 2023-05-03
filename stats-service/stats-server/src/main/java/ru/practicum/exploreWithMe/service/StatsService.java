package ru.practicum.exploreWithMe.service;

import ru.practicum.exploreWithMe.model.Hit;
import ru.practicum.exploreWithMe.model.ViewStats;
import ru.practicum.stats.dto.HitDto;

import java.util.List;

/**
 * Stats service
 */
public interface StatsService {

    /**
     * добавить hit
     */
    Hit postHit(HitDto hit);

    /**
     * просмотр статистики
     */
    List<ViewStats> viewStats(String start, String end, List<String> uris, Boolean unique);
}
