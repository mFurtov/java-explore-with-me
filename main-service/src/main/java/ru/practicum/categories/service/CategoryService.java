package ru.practicum.categories.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.dto.RequestCategoryDto;
import ru.practicum.categories.model.Category;

import java.util.List;

public interface CategoryService {
    CategoryDto postCategory(RequestCategoryDto requestCategoryDto);

    void dellCategory(int id);

    CategoryDto patchCategory(int id, RequestCategoryDto requestCategoryDto);

    List<CategoryDto> getAllCategory(Pageable sort);

    CategoryDto getCategoryById(int catId);

    public Category getCategoryNDtoById(int catId);
}
