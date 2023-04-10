package ru.practicum.mainService.dto.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.mainService.model.Category;
import ru.practicum.mainService.model.User;

import java.time.LocalDateTime;

/**
 * EventShortDto
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventShortDto {

    private Long id;
    private String annotation;
    private CategoryDto category;
    private Long confirmedRequests;
    private LocalDateTime eventDate;
    private UserShortDto initiator;
    private Boolean paid;
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
