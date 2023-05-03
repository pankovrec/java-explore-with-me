package ru.practicum.exploreWithMe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.exploreWithMe.model.Hit;
import ru.practicum.exploreWithMe.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Stats repository
 */

public interface StatsRepository extends JpaRepository<Hit, Long> {

    @Query("SELECT new ru.practicum.exploreWithMe.model.ViewStats(COUNT(h.ip), h.app, h.uri) " +
            "FROM Hit h " +
            "WHERE h.timestamp BETWEEN :start AND :end AND h.uri IN :uris " +
            "GROUP BY h.app, h.uri ORDER BY COUNT(h.ip) DESC")
    List<ViewStats> getAllStatsWithUris(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("SELECT new ru.practicum.exploreWithMe.model.ViewStats(COUNT(h.ip), h.app, h.uri) " +
            "FROM Hit h " +
            "WHERE h.timestamp BETWEEN :start AND :end " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(h.ip) DESC")
    List<ViewStats> getAllStatsWithoutUris(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new ru.practicum.exploreWithMe.model.ViewStats(COUNT (DISTINCT h.ip), h.app, h.uri) " +
            "FROM Hit h " +
            "WHERE h.timestamp BETWEEN :start AND :end AND h.uri IN :uris " +
            "GROUP BY h.app, h.uri ORDER BY COUNT(h.ip) DESC")
    List<ViewStats> getAllStatsWithUniqueIpWithUris(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("SELECT new ru.practicum.exploreWithMe.model.ViewStats(COUNT(DISTINCT h.ip), h.app, h.uri) " +
            "FROM Hit h " +
            "WHERE h.timestamp BETWEEN :start AND :end " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(DISTINCT h.ip) DESC")
    List<ViewStats> getAllStatsWithUniqueIpWithoutUris(LocalDateTime start, LocalDateTime end);

}
