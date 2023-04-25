package ru.practicum.mainService.dto.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.mainService.model.Request;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

/**
 * EventRequestStatusUpdateResult
 */

@NoArgsConstructor
@Setter
@Getter
public class RequestStatusUpdateResult {

    List<ParticipationRequestDto> confirmedRequests;
    List<ParticipationRequestDto> rejectedRequests;

    @NoArgsConstructor
    @Setter
    @Getter
    public static class ParticipationRequestDto {

        private Long id;
        private String created;
        private Long event;
        private Long requester;
        private String status;

        public static ParticipationRequestDto toParticipationRequestDto(Request request) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ROOT);
            ParticipationRequestDto dto = new ParticipationRequestDto();
            dto.setId(request.getId());
            dto.setRequester(request.getRequester().getId());
            dto.setEvent(request.getEvent().getId());
            dto.setCreated(request.getCreated().format(formatter));
            dto.setStatus(request.getStatus().name());
            return dto;
        }
    }
}
