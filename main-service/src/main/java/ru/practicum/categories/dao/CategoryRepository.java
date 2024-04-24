package ru.practicum.categories.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.categories.model.Category;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
    default Category findByIdOrThrow(Integer id) {
        Optional<Category> сategory = findById(id);
        if (сategory.isEmpty()) {
            throw new EmptyResultDataAccessException(String.format("Category with id=%d was not found",id) ,1);
        }else {
            return сategory.get();
        }
    }

}
