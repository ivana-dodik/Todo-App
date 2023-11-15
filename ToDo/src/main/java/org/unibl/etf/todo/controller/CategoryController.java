package org.unibl.etf.todo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.todo.dto.CategoryCreateDto;
import org.unibl.etf.todo.dto.CategoryReadDto;
import org.unibl.etf.todo.dto.CategoryUpdateDto;
import org.unibl.etf.todo.service.CategoryService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryReadDto>> getCategories() {
        return ResponseEntity.ok(categoryService.getCategories());
    }

    @PostMapping("/categories")
    public ResponseEntity<CategoryReadDto> addCategory(@RequestBody CategoryCreateDto categoryCreateDto) {
        return ResponseEntity.ok(categoryService.addCategory(categoryCreateDto));
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<CategoryReadDto> getCategory(@PathVariable("categoryId") Integer categoryId) {
        Optional<CategoryReadDto> categoryReadDto = categoryService.getCategory(categoryId);

        if (categoryReadDto.isPresent()) {
            return ResponseEntity.ok(categoryReadDto.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("categoryId") Integer categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<CategoryReadDto> editCategory(@PathVariable("categoryId") Integer categoryId, @RequestBody CategoryUpdateDto categoryUpdateDto) {
        Optional<CategoryReadDto> categoryReadDto = categoryService.editCategory(categoryId, categoryUpdateDto);

        if (categoryReadDto.isPresent()) {
            return ResponseEntity.ok(categoryReadDto.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
