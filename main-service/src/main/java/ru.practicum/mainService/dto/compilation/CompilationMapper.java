package ru.practicum.mainService.dto.compilation;

import ru.practicum.mainService.model.Compilation;

import java.util.stream.Collectors;

/**
 * Compilation mapper
 */

public class CompilationMapper {

    public static CompilationDto toCompilationDto(Compilation compilation) {
        CompilationDto dto = new CompilationDto();
        dto.setId(compilation.getId());
        dto.setPinned(compilation.getPinned());
        dto.setTitle(compilation.getTitle());
        dto.setEvents(compilation.getEvents().stream()
                .map(CompilationDto.EventShortDto::eventToEventShortDto)
                .collect(Collectors.toList()));
        return dto;
    }

    public static Compilation fromCompilationDto(NewCompilationDto compilationDto) {
        Compilation compilation = new Compilation();
        compilation.setPinned(compilationDto.getPinned());
        compilation.setTitle(compilationDto.getTitle());
        return compilation;
    }
}
