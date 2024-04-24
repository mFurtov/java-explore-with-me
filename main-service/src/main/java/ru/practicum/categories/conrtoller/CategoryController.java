package ru.practicum.categories.conrtoller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.Create;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.dto.RequestCategoryDto;
import ru.practicum.categories.service.CategoryService;

@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping("/admin/categories")
    public CategoryDto getCategory(@RequestBody @Validated(Create.class) RequestCategoryDto requestCategoryDto){
        return categoryService.postCategory(requestCategoryDto);
    }

    @DeleteMapping("/admin/categories/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void dellCategory(@PathVariable int id){
        categoryService.dellCategory(id);
    }

    @PatchMapping("/admin/categories/{id}")
    CategoryDto patchCategory(@PathVariable int id,@RequestBody RequestCategoryDto requestCategoryDto){
        return categoryService.patchCategory(id,requestCategoryDto);
    }
}
