package ru.practicum.categories.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import ru.practicum.categories.model.Category;
import ru.practicum.exception.NotFoundException;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    default Category findByIdOrThrow(Integer id) {
        Optional<Category> category = findById(id);
        if (category.isEmpty()) {
            throw new NotFoundException(String.format("Category with id=%d was not found", id), HttpStatus.NOT_FOUND);
        } else {
            return category.get();
        }
    }

}
