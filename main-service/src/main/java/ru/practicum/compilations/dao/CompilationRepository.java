package ru.practicum.compilations.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import ru.practicum.compilations.modul.Compilation;
import ru.practicum.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface CompilationRepository extends JpaRepository<Compilation, Integer> {
    default Compilation findByIdOrThrow(Integer id) {
        Optional<Compilation> compilation = findById(id);
        if (compilation.isEmpty()) {
            throw new NotFoundException(String.format("Category with id=%d was not found", id), HttpStatus.NOT_FOUND);
        } else {
            return compilation.get();
        }
    }

    List<Compilation> findByPinned(Boolean pinned, Pageable sort);
}
