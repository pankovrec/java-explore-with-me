package ru.practicum.mainService.service.admins;

import ru.practicum.mainService.dto.compilation.CompilationDto;
import ru.practicum.mainService.dto.compilation.NewCompilationDto;
import ru.practicum.mainService.dto.compilation.UpdateCompilationRequest;

/**
 * CompilationServiceAdmin
 */
public interface CompilationServiceAdmin {
    /**
     * Опубликовать подборку
     */
    CompilationDto postCompilation(NewCompilationDto newCompilationDto);

    /**
     * Обновить подборку
     */
    CompilationDto patchCompilation(Long compId, UpdateCompilationRequest updateCompilationRequest);

    /**
     * Удалить подборку
     */
    void deleteCompilation(Long compId);
}
