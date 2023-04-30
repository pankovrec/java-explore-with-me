package ru.practicum.exploreWithMe.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.exploreWithMe.mapper.StatsMapper;
import ru.practicum.exploreWithMe.model.Hit;
import ru.practicum.exploreWithMe.model.ViewStats;
import ru.practicum.exploreWithMe.repository.StatsRepository;
import ru.practicum.stats.dto.HitDto;

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
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Hit postHit(HitDto hit) {
        return repository.save(StatsMapper.toHit(hit));
    }

    @Override
    public List<ViewStats> viewStats(String start, String end, List<String> uris, Boolean unique) {

        LocalDateTime startDto = LocalDateTime.parse(URLDecoder.decode(start, StandardCharsets.UTF_8), formatter);
        LocalDateTime endDto = LocalDateTime.parse(URLDecoder.decode(end, StandardCharsets.UTF_8), formatter);

        List<ViewStats> result;
        if (uris == null || uris.isEmpty()) {
            if (unique) {
                result = repository.getAllStatsWithUniqueIpWithoutUris(startDto, endDto);
            } else {
                result = repository.getAllStatsWithoutUris(startDto, endDto);
            }
        } else {
            if (unique) {
                result = repository.getAllStatsWithUniqueIpWithUris(startDto, endDto, uris);
            } else {
                result = repository.getAllStatsWithUris(startDto, endDto, uris);
            }
        }
        return result;
    }
}
