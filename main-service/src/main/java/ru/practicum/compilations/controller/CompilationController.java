package ru.practicum.compilations.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.Create;
import ru.practicum.Update;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;
import ru.practicum.compilations.dto.UpdateCompilationRequest;
import ru.practicum.compilations.service.CompilationService;
import ru.practicum.pageable.PageableCreate;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CompilationController {
    private final CompilationService compilationService;

    @PostMapping("/admin/compilations")
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto postCompilation(@RequestBody @Validated(Create.class) NewCompilationDto newCompilationDto) {
        CompilationDto compilationDto = compilationService.postCompilation(newCompilationDto);
        log.info("Добавленна подборка {}", compilationDto.getId());
        return compilationDto;
    }

    @GetMapping("/compilations")
    public List<CompilationDto> getAllCompilation(@RequestParam(required = false) Boolean pinned, @RequestParam(defaultValue = "0") int from, @RequestParam(defaultValue = "10") int size) {
        List<CompilationDto> compilationDtos = compilationService.getAllCompilation(pinned, PageableCreate.getPageable(from, size, Sort.by(Sort.Direction.ASC, "id")));
        log.info("Выведен списко подборк {}", compilationDtos.size());
        return compilationDtos;
    }

    @GetMapping("/compilations/{compId}")
    public CompilationDto getAllCompilation(@PathVariable int compId) {
        CompilationDto compilationDto = compilationService.getCompilation(compId);
        log.info("Выведенна подборка {}", compilationDto.getId());
        return compilationDto;
    }

    @DeleteMapping("/admin/compilations/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void dellCompilation(@PathVariable int compId) {
        compilationService.dellCompilation(compId);
        log.info("Удаленна подборка {}", compId);
    }

    @PatchMapping("/admin/compilations/{compId}")
    public CompilationDto patchCompilation(@PathVariable int compId, @Validated(Update.class) @RequestBody UpdateCompilationRequest updateCompilationRequest) {
        CompilationDto compilationDto = compilationService.patchCompilation(compId, updateCompilationRequest);
        log.info("Удаленна подборка {}", compId);
        return compilationDto;
    }

}
