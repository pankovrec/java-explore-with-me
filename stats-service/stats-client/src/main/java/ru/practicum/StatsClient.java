package ru.practicum;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.stats.dto.HitDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * Stats client
 */

@Service
@Slf4j
public class StatsClient extends BaseClient {

    private final String application;

    @Autowired
    public StatsClient(@Value("${ewm_stats.url}") String serverUrl, RestTemplateBuilder builder, String application) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
        this.application = application;
    }

    public void hit(HttpServletRequest userRequest) {
        HitDto hit = HitDto.builder()
                .app(application)
                .ip(userRequest.getRemoteAddr())
                .uri(userRequest.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        post("/hit", hit);
    }
}
