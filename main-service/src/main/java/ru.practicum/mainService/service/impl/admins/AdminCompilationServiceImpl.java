package ru.practicum.mainService.service.impl.admins;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.mainService.dto.compilation.CompilationDto;
import ru.practicum.mainService.dto.compilation.CompilationMapper;
import ru.practicum.mainService.dto.compilation.NewCompilationDto;
import ru.practicum.mainService.dto.compilation.UpdateCompilationRequest;
import ru.practicum.mainService.model.Compilation;
import ru.practicum.mainService.model.Event;
import ru.practicum.mainService.repository.admins.CompilationRepositoryAdmin;
import ru.practicum.mainService.repository.admins.EventRepositoryAdmin;
import ru.practicum.mainService.service.admins.CompilationServiceAdmin;

import java.util.List;
import java.util.Optional;

/**
 * CompilationServiceAdminImpl
 */

@Service
public class AdminCompilationServiceImpl implements CompilationServiceAdmin {

    private final CompilationRepositoryAdmin repository;

    private final EventRepositoryAdmin eventRepository;

    @Autowired
    public AdminCompilationServiceImpl(CompilationRepositoryAdmin repository, EventRepositoryAdmin eventRepository) {
        this.repository = repository;
        this.eventRepository = eventRepository;
    }

    @Override
    public CompilationDto postCompilation(NewCompilationDto newCompilationDto) {
        Compilation compilation = CompilationMapper.fromCompilationDto(newCompilationDto);
        List<Event> events = eventRepository.findAllByIdIn(newCompilationDto.getEvents());
        compilation.setEvents(events);
        Compilation newCompilation = repository.save(compilation);
        return CompilationMapper.toCompilationDto(newCompilation);
    }

    @Override
    public CompilationDto patchCompilation(Long compId, UpdateCompilationRequest updateCompilationRequest) {

        Optional<Compilation> compilationOptional = repository.findById(compId);
        Compilation compilation = compilationOptional.get();

        if (updateCompilationRequest.getEvents() != null) {
            List<Event> events = eventRepository.findAllByIdIn(updateCompilationRequest.getEvents());
            compilation.setEvents(events);
        }

        if (updateCompilationRequest.getPinned() != null)
            compilation.setPinned(updateCompilationRequest.getPinned());

        Compilation updatedCompilation = repository.save(compilation);

        return CompilationMapper.toCompilationDto(updatedCompilation);
    }

    @Override
    public void deleteCompilation(Long compId) {

        Optional<Compilation> compilationOptional = repository.findById(compId);
        repository.delete(compilationOptional.get());
    }
}
