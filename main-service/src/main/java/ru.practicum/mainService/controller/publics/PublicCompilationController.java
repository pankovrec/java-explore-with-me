package ru.practicum.mainService.controller.publics;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainService.dto.compilation.CompilationDto;
import ru.practicum.mainService.service.publics.PublicCompilationService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * Compilation controller public
 */

@RestController
@Slf4j
@RequestMapping(path = "/compilations")
@Validated

public class PublicCompilationController {

    private final PublicCompilationService compilationService;

    @Autowired
    public PublicCompilationController(PublicCompilationService compilationService) {
        this.compilationService = compilationService;
    }

    @GetMapping
    public List<CompilationDto> getCompilations(@RequestParam(name = "pinned", required = false) Boolean pinned,
                                                @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Получен список подборок");
        return compilationService.getCompilations(pinned, from, size);
    }

    @GetMapping(path = "/{compId}")
    public ResponseEntity<CompilationDto> getCompilation(@Positive @PathVariable(name = "compId") Long compId) {
        CompilationDto compilationDto = compilationService.getCompilation(compId);
        log.info("Получена подборка с Id={}", compId);
        return new ResponseEntity<>(compilationDto, HttpStatus.OK);
    }
}
