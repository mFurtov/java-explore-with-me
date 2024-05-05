package ru.practicum.compilations.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;
import ru.practicum.compilations.dto.UpdateCompilationRequest;

import java.util.List;

public interface CompilationService {
    CompilationDto postCompilation(NewCompilationDto newCompilationDto);

    List<CompilationDto> getAllCompilation(Boolean pinned, Pageable sort);

    CompilationDto getCompilation(int compId);

    void dellCompilation(int compId);

    CompilationDto patchCompilation(int compId, UpdateCompilationRequest updateCompilationRequest);
}
