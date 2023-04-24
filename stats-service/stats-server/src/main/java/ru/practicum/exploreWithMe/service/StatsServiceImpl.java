package ru.practicum.exploreWithMe.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.exploreWithMe.model.Hit;
import ru.practicum.exploreWithMe.model.ViewStats;
import ru.practicum.exploreWithMe.repository.StatsRepository;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Stats service impl
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class StatsServiceImpl implements StatsService {
    private final StatsRepository repository;
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Hit saveHit(Hit hit) {
        return repository.save(hit);
    }

    @Override
    public List<ViewStats> viewStats(String start, String end, List<String> uris, Boolean unique) {
        LocalDateTime startDto;
        LocalDateTime endDto;
        if (start != null && end != null) {
            startDto = LocalDateTime.parse(URLDecoder.decode(start, StandardCharsets.UTF_8), dateTimeFormatter);
            endDto = LocalDateTime.parse(URLDecoder.decode(end, StandardCharsets.UTF_8), dateTimeFormatter);
        } else {
            startDto = LocalDateTime.MIN;
            endDto = LocalDateTime.MAX;
        }
        List<ViewStats> result;
        if (uris == null || uris.isEmpty()) {
            if (unique) {
                result = repository.getAllStatsUniqueIp(startDto, endDto);
            } else {
                result = repository.getAllStats(startDto, endDto);
            }
        } else {
            if (unique) {
                result = repository.getAllWithUniqueIp(startDto, endDto, uris);
            } else {
                result = repository.getAll(startDto, endDto, uris);
            }
        }
        return result;
    }
}
