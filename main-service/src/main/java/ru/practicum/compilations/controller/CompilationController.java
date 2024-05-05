package ru.practicum.compilations.controller;

import lombok.RequiredArgsConstructor;
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
public class CompilationController {
    private final CompilationService compilationService;

    @PostMapping("/admin/compilations")
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto postCompilation(@RequestBody @Validated(Create.class) NewCompilationDto newCompilationDto) {
        return compilationService.postCompilation(newCompilationDto);
    }

    @GetMapping("/compilations")
    public List<CompilationDto> getAllCompilation(@RequestParam(required = false) Boolean pinned, @RequestParam(defaultValue = "0") int from, @RequestParam(defaultValue = "10") int size) {
        return compilationService.getAllCompilation(pinned, PageableCreate.getPageable(from, size, Sort.by(Sort.Direction.ASC, "id")));
    }

    @GetMapping("/compilations/{compId}")
    public CompilationDto getAllCompilation(@PathVariable int compId) {
        return compilationService.getCompilation(compId);
    }

    @DeleteMapping("/admin/compilations/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void dellCompilation(@PathVariable int compId) {
        compilationService.dellCompilation(compId);
    }

    @PatchMapping("/admin/compilations/{compId}")
    public CompilationDto patchCompilation(@PathVariable int compId, @Validated(Update.class) @RequestBody UpdateCompilationRequest updateCompilationRequest) {
        return compilationService.patchCompilation(compId, updateCompilationRequest);
    }

}
