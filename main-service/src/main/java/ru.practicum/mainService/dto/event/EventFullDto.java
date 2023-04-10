package ru.practicum.mainService.dto.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.mainService.model.Category;
import ru.practicum.mainService.model.Location;
import ru.practicum.mainService.model.State;
import ru.practicum.mainService.model.User;

import java.time.LocalDateTime;

/**
 * Event full dto
 */

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EventFullDto {

    private Long id;
    private String annotation;
    private CategoryDto category;
    private Long confirmedRequests;
    private LocalDateTime createdOn;
    private String description;
    private LocalDateTime eventDate;
    private UserShortDto initiator;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private LocalDateTime publishedOn;
    private Boolean requestModeration;
    private State state;
    private String title;
    private Long views;

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    public static class CategoryDto {
        private Long id;
        private String name;

        public static CategoryDto categoryToCategoryDto(Category entity) {
            return new CategoryDto(entity.getId(),
                    entity.getName());
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    public static class UserShortDto {
        private Long id;
        private String name;

        public static UserShortDto userToUserShortDto(User entity) {
            UserShortDto dto = new UserShortDto();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            return dto;
        }
    }
}
