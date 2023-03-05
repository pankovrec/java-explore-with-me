package ru.practicum.exploreWithMe.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Stats model
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ViewStats {
    private Long hits;
    private String app;
    private String uri;
}
