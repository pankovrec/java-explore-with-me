package ru.practicum.mainService.dto.compilation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Update compilation Request dto
 */

@NoArgsConstructor
@Setter
@Getter
public class UpdateCompilationRequest {
    @NotNull
    private List<Long> events;
    private Boolean pinned;
    @NotNull
    private String title;
}
