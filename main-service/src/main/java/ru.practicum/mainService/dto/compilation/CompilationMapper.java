package ru.practicum.mainService.dto.compilation;

import ru.practicum.mainService.model.Compilation;

import java.util.stream.Collectors;

/**
 * Compilation mapper
 */

public class CompilationMapper {

    public static CompilationDto toCompilationDto(Compilation entity) {
        CompilationDto dto = new CompilationDto();
        dto.setId(entity.getId());
        dto.setPinned(entity.getPinned());
        dto.setTitle(entity.getTitle());
        dto.setEvents(entity.getEvents().stream()
                .map(CompilationDto.EventShortDto::eventToEventShortDto)
                .collect(Collectors.toList()));
        return dto;
    }

    public static Compilation fromCompilationDto(NewCompilationDto dto) {
        Compilation compilation = new Compilation();
        compilation.setPinned(dto.getPinned());
        compilation.setTitle(dto.getTitle());
        return compilation;
    }
}
