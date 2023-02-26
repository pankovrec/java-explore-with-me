package ru.practicum.exploreWithMe.mapper;

import ru.practicum.exploreWithMe.model.Hit;
import org.springframework.stereotype.Component;
import ru.practicum.stats.dto.HitDto;

/**
 * Stats Mapper
 */

@Component
public class StatsMapper {
    public Hit toHit(HitDto hitDto) {
        Hit hit = new Hit();
        hit.setApp(hitDto.getApp());
        hit.setUri(hitDto.getUri());
        hit.setIp(hitDto.getIp());
        hit.setTimestamp(hitDto.getTimestamp());
        return hit;
    }

    public HitDto toHitDto(Hit hit) {
        HitDto dto = new HitDto();
        dto.setApp(hit.getApp());
        dto.setUri(hit.getUri());
        dto.setIp(hit.getIp());
        dto.setTimestamp(hit.getTimestamp());
        return dto;
    }
}
