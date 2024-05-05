package ru.practicum.compilations.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;
import ru.practicum.compilations.modul.Compilation;
import ru.practicum.events.mapper.EventMapper;
import ru.practicum.events.model.Event;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class CompilationMapper {
    public Compilation mapCompilationFromDto(NewCompilationDto newCompilationDto, List<Event> events) {
        return new Compilation(newCompilationDto.getTitle(), newCompilationDto.getPinned(), events);
    }

    public CompilationDto mapCompilationToDto(Compilation compilation) {
        return new CompilationDto(EventMapper.mapEventShortFromEventToList(compilation.getEvents()), compilation.getId(), compilation.isPinned(), compilation.getTitle());
    }

    public List<CompilationDto> mapCompilationToDtoList(Iterable<Compilation> compilation) {
        List<CompilationDto> result = new ArrayList<>();
        for (Compilation c : compilation) {
            result.add(mapCompilationToDto(c));
        }
        return result;
    }
}
