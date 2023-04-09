package ru.practicum.mainService.service.impl.publics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ru.practicum.mainService.dto.compilation.CompilationDto;
import ru.practicum.mainService.dto.compilation.CompilationMapper;
import ru.practicum.mainService.model.Compilation;
import ru.practicum.mainService.repository.publics.CompilationRepositoryPublic;
import ru.practicum.mainService.service.publics.CompilationServicePublic;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * CompilationServicePublicImpl
 */

@Service
public class CompilationServicePublicImpl implements CompilationServicePublic {

    private final CompilationRepositoryPublic repository;

    @Autowired
    public CompilationServicePublicImpl(CompilationRepositoryPublic repository) {
        this.repository = repository;
    }

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {
        Pageable pageRequest = PageRequest.of((from / size), size);
        List<Compilation> compilations;
        if (pinned != null) {
            compilations = repository.findAllByPinned(pinned, pageRequest);
        } else {
            compilations = repository.findAll(pageRequest).toList();
        }

        return compilations.stream()
                .map(CompilationMapper::toCompilationDto).collect(Collectors.toList());
    }

    @Override
    public CompilationDto getCompilationById(Long compId) {
        Optional<Compilation> compilation = repository.findById(compId);
        return CompilationMapper.toCompilationDto(compilation.get());
    }
}
