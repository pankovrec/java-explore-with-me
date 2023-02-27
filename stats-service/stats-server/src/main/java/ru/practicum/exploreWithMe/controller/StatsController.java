package ru.practicum.exploreWithMe.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.mapper.StatsMapper;
import ru.practicum.exploreWithMe.service.StatsService;
import ru.practicum.exploreWithMe.model.ViewStats;
import ru.practicum.stats.dto.HitDto;

import javax.validation.Valid;
import java.util.List;

/**
 * Stats controller
 */

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatsController {
    private final StatsService statsService;
    private final StatsMapper mapper;

    @PostMapping("/hit")
    public ResponseEntity<HitDto> hit(@Valid @RequestBody HitDto hitDto) {
        log.info("Обрабатываю запрос /hit");
        return new ResponseEntity<>(
                mapper.toHitDto(statsService.saveHit(mapper.toHit(hitDto))), HttpStatus.CREATED);
    }

    @GetMapping("/stats")
    public List<ViewStats> getViewStats(
            String start,
            String end,
            @RequestParam List<String> uris,
            @RequestParam(defaultValue = "false") Boolean unique) {
        log.info("Обрабатываю запрос /stats", start, end, uris, unique);
        return statsService.viewStats(start, end, uris, unique);
    }
}
