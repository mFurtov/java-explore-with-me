package ru.practicum.categories.conrtoller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.Create;
import ru.practicum.Update;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.dto.RequestCategoryDto;
import ru.practicum.categories.service.CategoryService;
import ru.practicum.pageable.PageableCreate;

import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/admin/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto getCategory(@RequestBody @Validated(Create.class) RequestCategoryDto requestCategoryDto) {
        CategoryDto categoryDto = categoryService.postCategory(requestCategoryDto);
        log.info("Добавлена категория {}", categoryDto.getName());
        return categoryDto;
    }

    @DeleteMapping("/admin/categories/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void dellCategory(@PathVariable int id) {
        categoryService.dellCategory(id);
        log.info("Удалена категория {}", id);
    }

    @PatchMapping("/admin/categories/{id}")
    public CategoryDto patchCategory(@PathVariable int id, @RequestBody @Validated(Update.class) RequestCategoryDto requestCategoryDto) {
        CategoryDto categoryDto = categoryService.patchCategory(id, requestCategoryDto);
        log.info("Обновленна категория {}", id);
        return categoryDto;
    }

    @GetMapping("/categories")
    public List<CategoryDto> getAllCategory(@RequestParam(defaultValue = "0") @PositiveOrZero int from, @RequestParam(defaultValue = "10") @Min(1) int size) {
        List<CategoryDto> categoryDtos = categoryService.getAllCategory(PageableCreate.getPageable(from, size, Sort.by(Sort.Direction.ASC, "id")));
        log.info("Выведенк список категорий {}", categoryDtos.size());
        return categoryDtos;
    }

    @GetMapping("/categories/{catId}")
    public CategoryDto getCategoryById(@PathVariable int catId) {
        CategoryDto categoryDto = categoryService.getCategoryById(catId);
        log.info("Выведенна категория {}", catId);
        return categoryDto;
    }
}
