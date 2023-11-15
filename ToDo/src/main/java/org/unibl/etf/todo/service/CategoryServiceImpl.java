package org.unibl.etf.todo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.unibl.etf.todo.domain.Category;
import org.unibl.etf.todo.dto.CategoryCreateDto;
import org.unibl.etf.todo.dto.CategoryReadDto;
import org.unibl.etf.todo.dto.CategoryUpdateDto;
import org.unibl.etf.todo.mapper.CategoryMapper;
import org.unibl.etf.todo.repo.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryReadDto> getCategories() {
        var categories = categoryRepository.getCategories();
        return categoryMapper.toDtoList(categories);
    }

    @Override
    public CategoryReadDto addCategory(CategoryCreateDto category) {
        Category c = categoryRepository.addCategory(category);

        return categoryMapper.toDto(c);
    }

    @Override
    public Optional<CategoryReadDto> getCategory(Integer categoryId) {
        Optional<Category> category = categoryRepository.getCategory(categoryId);

        return category.map(categoryMapper::toDto);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        categoryRepository.deleteCategory(categoryId);
    }

    @Override
    public Optional<CategoryReadDto> editCategory(int categoryId, CategoryUpdateDto categoryUpdateDto) {
        Optional<Category> category = categoryRepository.editCategory(categoryId, categoryUpdateDto);

        return category.map(categoryMapper::toDto);
    }
}
