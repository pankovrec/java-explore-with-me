package ru.practicum.mainService.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.mainService.model.Category;
import ru.practicum.mainService.model.Location;
import ru.practicum.mainService.model.State;
import ru.practicum.mainService.model.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @NotBlank
    private String annotation;
    @NotNull
    private CategoryDto category;
    private Long confirmedRequests;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;
    @NotBlank
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private UserShortDto initiator;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn;
    private Boolean requestModeration;
    private State state;
    @NotBlank
    private String title;
    private Long views;

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    public static class CategoryDto {
        private Long id;
        @NotNull
        private String name;

        public static CategoryDto categoryToCategoryDto(Category category) {
            return new CategoryDto(category.getId(),
                    category.getName());
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    public static class UserShortDto {
        private Long id;
        @NotNull
        private String name;

        public static UserShortDto userToUserShortDto(User user) {
            UserShortDto dto = new UserShortDto();
            dto.setId(user.getId());
            dto.setName(user.getName());
            return dto;
        }
    }
}
