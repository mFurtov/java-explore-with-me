package ru.practicum.categories.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.categories.dao.CategoryRepository;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.dto.RequestCategoryDto;
import ru.practicum.categories.mapper.CategoryMapper;
import ru.practicum.categories.model.Category;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto postCategory(RequestCategoryDto requestCategoryDto) {
        return CategoryMapper.mapCategoryDtoFromCategory(categoryRepository.save(CategoryMapper.mapCategoryFromNewCategory(requestCategoryDto)));
    }

    public void dellCategory(int id) {
        Category category = categoryRepository.findByIdOrThrow(id);
        categoryRepository.deleteById(id);
    }

    public CategoryDto patchCategory(int id, RequestCategoryDto requestCategoryDto) {
        Category category = categoryRepository.findByIdOrThrow(id);
        category.setName(requestCategoryDto.getName());
        return CategoryMapper.mapCategoryDtoFromCategory(categoryRepository.save(category));
    }

    @Override
    public List<CategoryDto> getAllCategory(Pageable sort) {
        return CategoryMapper.mapCategoryDtoFromCategoryList(categoryRepository.findAll(sort));
    }

    @Override
    public CategoryDto getCategoryById(int catId) {
        return CategoryMapper.mapCategoryDtoFromCategory(categoryRepository.findByIdOrThrow(catId));
    }

    public Category getCategoryNDtoById(int catId) {
        return categoryRepository.getById(catId);
    }
}