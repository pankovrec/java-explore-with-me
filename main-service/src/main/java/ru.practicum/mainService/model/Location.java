package ru.practicum.mainService.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * LocationModel
 */

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Location {
    private Double lat;
    private Double lon;
}
