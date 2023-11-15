package org.unibl.etf.todo.repo;

import org.unibl.etf.todo.domain.Category;
import org.unibl.etf.todo.dto.CategoryCreateDto;
import org.unibl.etf.todo.dto.CategoryUpdateDto;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    List<Category> getCategories();

    Category addCategory(CategoryCreateDto category);

    Optional<Category> getCategory(Integer categoryId);

    void deleteCategory(Integer categoryId);

    Optional<Category> editCategory(int categoryId, CategoryUpdateDto categoryUpdateDto);
}
