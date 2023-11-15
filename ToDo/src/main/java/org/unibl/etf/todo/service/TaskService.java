package org.unibl.etf.todo.service;

import org.unibl.etf.todo.dto.TaskCreateDto;
import org.unibl.etf.todo.dto.TaskReadDto;
import org.unibl.etf.todo.dto.TaskUpdateDto;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<TaskReadDto> getTasks(Integer categoryId);

    Optional<TaskReadDto> addTask(Integer categoryId, TaskCreateDto taskCreateDto);

    void deleteTask(Integer taskId);

    Optional<TaskReadDto> editTask(int taskId, TaskUpdateDto taskUpdateDto);

    Optional<TaskReadDto> toggleComplete(int taskId);
}
