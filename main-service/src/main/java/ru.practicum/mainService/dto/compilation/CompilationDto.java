package ru.practicum.mainService.dto.compilation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.mainService.model.Category;
import ru.practicum.mainService.model.Event;
import ru.practicum.mainService.model.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Compilation dto
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {

    private Long id;
    private List<EventShortDto> events;
    private Boolean pinned;
    @NotBlank
    private String title;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class EventShortDto {

        private Long id;
        @NotBlank
        private String annotation;
        @NotNull
        private CategoryDto category;
        private Long confirmedRequests;
        @NotNull
        private LocalDateTime eventDate;
        private UserShortDto initiator;
        private Boolean paid;
        @NotNull
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

        public static EventShortDto eventToEventShortDto(Event event) {
            EventShortDto dto = new EventShortDto();
            dto.setId(event.getId());
            dto.setAnnotation(event.getAnnotation());
            dto.setCategory(CategoryDto.categoryToCategoryDto(event.getCategory()));
            dto.setConfirmedRequests(event.getConfirmedRequests());
            dto.setEventDate(event.getEventDate());
            dto.setInitiator(UserShortDto.entityToUserShortDto(event.getInitiator()));
            dto.setPaid(event.getPaid());
            dto.setTitle(event.getTitle());
            dto.setViews(event.getViews());
            return dto;
        }

        @AllArgsConstructor
        @NoArgsConstructor
        @Setter
        @Getter
        public static class UserShortDto {
            private Long id;
            @NotBlank
            private String name;

            public static UserShortDto entityToUserShortDto(User user) {
                UserShortDto dto = new UserShortDto();
                dto.setId(user.getId());
                dto.setName(user.getName());
                return dto;
            }
        }
    }
}

