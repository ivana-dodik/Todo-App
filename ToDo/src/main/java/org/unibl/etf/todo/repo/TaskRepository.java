package org.unibl.etf.todo.repo;

import org.unibl.etf.todo.domain.Task;
import org.unibl.etf.todo.dto.TaskCreateDto;
import org.unibl.etf.todo.dto.TaskUpdateDto;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    List<Task> getTasks(Integer categoryId);

    Optional<Task> addTask(Integer categoryId, TaskCreateDto taskCreateDto);

    void deleteTask(Integer taskId);

    Optional<Task> editTask(int taskId, TaskUpdateDto taskUpdateDto, Task task);

    Optional<Task> getTaskById(int taskId);

    Optional<Task> toggleComplete(int taskId);
}
