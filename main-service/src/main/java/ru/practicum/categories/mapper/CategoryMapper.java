package ru.practicum.categories.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.dto.RequestCategoryDto;
import ru.practicum.categories.model.Category;

@UtilityClass
public class CategoryMapper {
    public Category mapCategoryFromNewCategory(RequestCategoryDto requestCategoryDto){
        return new Category(requestCategoryDto.getName());
    }
    public CategoryDto mapCategoryDtoFromCategory(Category category){
        return new CategoryDto(category.getId(),category.getName());
    }
}
