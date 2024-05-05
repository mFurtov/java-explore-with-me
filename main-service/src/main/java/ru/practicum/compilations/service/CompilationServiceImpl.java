package ru.practicum.compilations.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.practicum.categories.service.CategoryService;
import ru.practicum.compilations.dao.CompilationRepository;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;
import ru.practicum.compilations.dto.UpdateCompilationRequest;
import ru.practicum.compilations.mapper.CompilationMapper;
import ru.practicum.compilations.modul.Compilation;
import ru.practicum.events.dao.EventRepository;
import ru.practicum.events.model.Event;
import ru.practicum.exception.ConflictException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final EventRepository eventRepository;
    private final CompilationRepository compilationRepository;
    private final CategoryService categoryService;

    @Override
    public CompilationDto postCompilation(NewCompilationDto newCompilationDto) {
        List<Event> event;
        if (newCompilationDto.getEvents() != null) {
            event = eventRepository.findAllById(newCompilationDto.getEvents());
        } else {
            event = new ArrayList<>();
        }
        try {
            return CompilationMapper.mapCompilationToDto(compilationRepository.save(CompilationMapper.mapCompilationFromDto(newCompilationDto, event)));
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @Override
    public List<CompilationDto> getAllCompilation(Boolean pinned, Pageable sort) {
        if (pinned == null) {
            return CompilationMapper.mapCompilationToDtoList(compilationRepository.findAll(sort));
        } else {
            return CompilationMapper.mapCompilationToDtoList(compilationRepository.findByPinned(pinned, sort));
        }
    }

    @Override
    public CompilationDto getCompilation(int compId) {
        return CompilationMapper.mapCompilationToDto(compilationRepository.findByIdOrThrow(compId));
    }

    @Override
    public void dellCompilation(int compId) {
        compilationRepository.findByIdOrThrow(compId);
        compilationRepository.deleteById(compId);
    }

    @Override
    public CompilationDto patchCompilation(int compId, UpdateCompilationRequest updateCompilationRequest) {
        Compilation compilation = compilationRepository.getById(compId);
        if (updateCompilationRequest.getEvents() != null) {
            List<Event> event = eventRepository.findAllById(updateCompilationRequest.getEvents());
            compilation.setEvents(event);
        }
        if (updateCompilationRequest.getPinned() != null) {
            compilation.setPinned(updateCompilationRequest.getPinned());
        }
        if (updateCompilationRequest.getTitle() != null) {
            compilation.setTitle(updateCompilationRequest.getTitle());
        }
        return CompilationMapper.mapCompilationToDto(compilationRepository.save(compilation));
    }
}
