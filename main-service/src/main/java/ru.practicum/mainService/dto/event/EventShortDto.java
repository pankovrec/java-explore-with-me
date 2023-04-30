package ru.practicum.mainService.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.mainService.model.Category;
import ru.practicum.mainService.model.User;

import javax.validation.constraints.NotBlank;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
        @NotBlank
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
        @NotBlank
        private String name;

        public static UserShortDto userToUserShortDto(User user) {
            UserShortDto userShortDto = new UserShortDto();
            userShortDto.setId(user.getId());
            userShortDto.setName(user.getName());
            return userShortDto;
        }
    }
}
