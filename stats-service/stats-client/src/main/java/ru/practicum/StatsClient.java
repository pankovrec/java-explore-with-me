package ru.practicum;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.stats.dto.HitDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * Stats client
 */

@Service
@Slf4j
public class StatsClient extends BaseClient {

@Autowired
public StatsClient(@Value("${stats-service.url}") String serverUrl, RestTemplateBuilder builder) {
    super(
            builder
                    .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                    .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                    .build()
    );
    log.info("creating stats with url = {}", serverUrl);
}

    public ResponseEntity<Object> sendHit(HitDto hitDto) {
        return post("/hit", hitDto);
    }

    public ResponseEntity<Object> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> parameters = Map.of(
                "start",  start.format(formatter),
                "end",    end.format(formatter),
                "uris",   uris,
                "unique", unique);

        log.info("get stats from = {} end = {} uris = {} ids = {}",
                parameters.get("start"), parameters.get("end"), parameters.get("uris"), parameters.get("unique"));

        return get("/stats?start={start}&end={end}&uris={uris}&unique={unique}", parameters);
    }
}
