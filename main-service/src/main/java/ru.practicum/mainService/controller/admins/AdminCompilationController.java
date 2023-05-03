package ru.practicum.mainService.controller.admins;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainService.dto.compilation.CompilationDto;
import ru.practicum.mainService.dto.compilation.NewCompilationDto;
import ru.practicum.mainService.dto.compilation.UpdateCompilationRequest;
import ru.practicum.mainService.service.admins.AdminCompilationService;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

/**
 * Compilation controller admin
 */

@RestController
@RequestMapping(path = "/admin/compilations")
@Validated
@Slf4j

public class AdminCompilationController {

    private final AdminCompilationService compilationService;

    @Autowired
    public AdminCompilationController(AdminCompilationService compilationService) {
        this.compilationService = compilationService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<CompilationDto> postCompilation(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        CompilationDto compilationDto = compilationService.postCompilation(newCompilationDto);
        log.info("Опубликована подборка {}", newCompilationDto);
        return new ResponseEntity<>(compilationDto, HttpStatus.CREATED);
    }

    @PatchMapping(path = "/{compId}")
    @Transactional
    public CompilationDto patchCompilation(@Positive @PathVariable(name = "compId") Long compId,
                                           @RequestBody UpdateCompilationRequest updateCompilationRequest) {
        log.info("Обновлена подборка с Id={}", compId);
        return compilationService.patchCompilation(compId, updateCompilationRequest);
    }

    @DeleteMapping(path = "/{compId}")
    @Transactional
    public ResponseEntity<Object> deleteCompilation(@Positive @PathVariable(name = "compId") Long compId) {
        compilationService.deleteCompilation(compId);
        log.info("Удалена подборка с Id={}", compId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
