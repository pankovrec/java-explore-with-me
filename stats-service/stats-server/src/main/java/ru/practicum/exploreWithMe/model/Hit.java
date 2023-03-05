package ru.practicum.exploreWithMe.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Hit model
 */

@Entity
@Table(name = "statistics")
@NoArgsConstructor
@Getter
@Setter
public class Hit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "app", length = 50, nullable = false)
    private String app;
    @Column(name = "uri", length = 100, nullable = false)
    private String uri;
    @Column(name = "ip", length = 50, nullable = false)
    private String ip;
    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    public Hit(Long id, String app, String uri, String ip, LocalDateTime timestamp) {
        this.id = id;
        this.app = app;
        this.uri = uri;
        this.ip = ip;
        this.timestamp = timestamp;
    }
}
