package ru.practicum.mainService.service.impl.admins;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.mainService.dto.compilation.CompilationDto;
import ru.practicum.mainService.dto.compilation.CompilationMapper;
import ru.practicum.mainService.dto.compilation.NewCompilationDto;
import ru.practicum.mainService.dto.compilation.UpdateCompilationRequest;
import ru.practicum.mainService.model.Compilation;
import ru.practicum.mainService.model.Event;
import ru.practicum.mainService.repository.admins.AdminCompilationRepository;
import ru.practicum.mainService.repository.admins.AdminEventRepository;
import ru.practicum.mainService.service.admins.AdminCompilationService;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * CompilationServiceAdminImpl
 */

@Service
public class AdminCompilationServiceImpl implements AdminCompilationService {

    private final AdminCompilationRepository repository;

    private final AdminEventRepository eventRepository;

    @Autowired
    public AdminCompilationServiceImpl(AdminCompilationRepository repository, AdminEventRepository eventRepository) {
        this.repository = repository;
        this.eventRepository = eventRepository;
    }

    @Override
    public CompilationDto postCompilation(NewCompilationDto newCompilationDto) {
        Compilation compilation = CompilationMapper.fromCompilationDto(newCompilationDto);
        List<Event> events = eventRepository.findAllByIdIn(newCompilationDto.getEvents());
        compilation.setEvents(events);
        return CompilationMapper.toCompilationDto(repository.save(compilation));
    }

    @Override
    public CompilationDto patchCompilation(Long compId, UpdateCompilationRequest updateCompilationRequest) {

        Compilation compilation = repository.findById(compId).orElseThrow(NoSuchElementException::new);

        if (updateCompilationRequest.getEvents() != null) {
            compilation.setEvents(eventRepository.findAllByIdIn(updateCompilationRequest.getEvents()));
        }

        if (updateCompilationRequest.getPinned() != null)
            compilation.setPinned(updateCompilationRequest.getPinned());

        return CompilationMapper.toCompilationDto(repository.save(compilation));
    }

    @Override
    public void deleteCompilation(Long compId) {
        repository.delete(repository.findById(compId).orElseThrow(NoSuchElementException::new));
    }
}
