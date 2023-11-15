package org.unibl.etf.todo.repo;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.unibl.etf.todo.domain.Category;
import org.unibl.etf.todo.domain.Priority;
import org.unibl.etf.todo.domain.Task;
import org.unibl.etf.todo.dto.CategoryCreateDto;
import org.unibl.etf.todo.dto.CategoryUpdateDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Category> getCategories() {
        String sql = "SELECT * FROM category";

        return jdbcTemplate.query(sql, new CategoryRowMapper());
    }

    @Override
    public Category addCategory(CategoryCreateDto category) {
        String sql = "INSERT INTO category (name) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"category_id"});
            ps.setString(1, category.name());
            return ps;
        }, keyHolder);

        int id = Objects.requireNonNull(keyHolder.getKey()).intValue();

        return new Category(id, category.name(), List.of());
    }

    @Override
    public Optional<Category> getCategory(Integer categoryId) {
        String sql = "SELECT c.name AS category_name, t.* FROM category c LEFT JOIN task t ON c.category_id = t.category_id WHERE c.category_id = ?";

        try {
            Category category = jdbcTemplate.queryForObject(sql, new CategoryWithTasksRowMapper(), categoryId);
            return Optional.ofNullable(category);
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        String sql = "DELETE FROM category WHERE category_id = ?";

        jdbcTemplate.update(sql, categoryId);
    }

    @Override
    public Optional<Category> editCategory(int categoryId, CategoryUpdateDto categoryUpdateDto) {
        String newName = categoryUpdateDto.name();
        String sql = "UPDATE category SET name = ? WHERE category_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, newName, categoryId);
        if (rowsAffected == 0) {
            return Optional.empty();
        } else {
            Category updatedCategory = new Category(categoryId, newName, List.of());
            return Optional.of(updatedCategory);
        }
    }

    private static class CategoryRowMapper implements RowMapper<Category> {
        @Override
        public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Category(rs.getInt("category_id"), rs.getString("name"), List.of());
        }
    }

    public static class CategoryWithTasksRowMapper implements RowMapper<Category> {
        @Override
        public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
            int categoryId = rs.getInt("category_id");
            String categoryName = rs.getString("category_name");
            List<Task> tasks = new ArrayList<>();
            do {
                int taskId = rs.getInt("task_id");
                if (!rs.wasNull()) {
                    String taskName = rs.getString("name");
                    Timestamp timestamp = rs.getTimestamp("due");
                    LocalDateTime taskDue = null;
                    if (timestamp != null) {
                        taskDue = timestamp.toLocalDateTime();
                    }
                    Priority taskPriority = Priority.valueOf(rs.getString("priority"));
                    boolean taskCompleted = rs.getBoolean("completed");
                    Task task = new Task(taskId, taskName, taskDue, taskPriority, taskCompleted);
                    tasks.add(task);
                }
            } while (rs.next() && rs.getInt("category_id") == categoryId);
            return new Category(categoryId, categoryName, tasks);
        }
    }

}
