package ru.practicum.categories.service;

import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.dto.RequestCategoryDto;

public interface CategoryService {
    CategoryDto postCategory(RequestCategoryDto requestCategoryDto);
    void dellCategory(int id);
    CategoryDto patchCategory(int id,RequestCategoryDto requestCategoryDto);
}
