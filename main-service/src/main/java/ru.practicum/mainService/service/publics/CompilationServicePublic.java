package ru.practicum.mainService.service.publics;

import ru.practicum.mainService.dto.compilation.CompilationDto;

import java.util.List;

/**
 * CompilationServicePublic
 */
public interface CompilationServicePublic {

    /**
     * получить список подборок
     */
    List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size);

    /**
     * получить подборку по id
     */
    CompilationDto getCompilationById(Long compId);
}
