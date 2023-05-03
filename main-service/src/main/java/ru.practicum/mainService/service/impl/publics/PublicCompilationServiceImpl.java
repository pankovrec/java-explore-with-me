package ru.practicum.mainService.service.impl.publics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.mainService.dto.compilation.CompilationDto;
import ru.practicum.mainService.dto.compilation.CompilationMapper;
import ru.practicum.mainService.model.Compilation;
import ru.practicum.mainService.repository.publics.PublicCompilationRepository;
import ru.practicum.mainService.service.publics.PublicCompilationService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * CompilationServicePublicImpl
 */

@Service
public class PublicCompilationServiceImpl implements PublicCompilationService {

    private final PublicCompilationRepository repository;

    @Autowired
    public PublicCompilationServiceImpl(PublicCompilationRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {
        Pageable pageRequest = PageRequest.of((from / size), size);
        List<Compilation> compilations;
        if (pinned == null) {
            compilations = repository.findAll(pageRequest).toList();
        } else {
            compilations = repository.findAllByPinned(pinned, pageRequest);
        }
        return compilations.stream().map(CompilationMapper::toCompilationDto).collect(Collectors.toList());
    }

    @Override
    public CompilationDto getCompilation(Long compId) {
        return CompilationMapper.toCompilationDto(repository.findById(compId).orElseThrow(NoSuchElementException::new));
    }
}
