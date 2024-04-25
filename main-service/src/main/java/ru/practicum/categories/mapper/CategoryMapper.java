package ru.practicum.categories.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.dto.RequestCategoryDto;
import ru.practicum.categories.model.Category;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class CategoryMapper {
    public Category mapCategoryFromNewCategory(RequestCategoryDto requestCategoryDto){
        return new Category(requestCategoryDto.getName());
    }
    public List<CategoryDto> mapCategoryDtoFromCategoryList(Iterable<Category> category) {
        List<CategoryDto> result = new ArrayList<>();
        for (Category c :category) {
            result.add(mapCategoryDtoFromCategory(c));
        }
        return result;
    }
    public CategoryDto mapCategoryDtoFromCategory(Category category){
        return new CategoryDto(category.getId(),category.getName());
    }
}
