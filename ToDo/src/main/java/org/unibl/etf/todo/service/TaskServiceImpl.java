package org.unibl.etf.todo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unibl.etf.todo.domain.Task;
import org.unibl.etf.todo.dto.TaskCreateDto;
import org.unibl.etf.todo.dto.TaskReadDto;
import org.unibl.etf.todo.dto.TaskUpdateDto;
import org.unibl.etf.todo.mapper.TaskMapper;
import org.unibl.etf.todo.repo.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public List<TaskReadDto> getTasks(Integer categoryId) {
        var tasks = taskRepository.getTasks(categoryId);

        return taskMapper.toTaskReadDtoList(tasks);
    }

    @Override
    public Optional<TaskReadDto> addTask(Integer categoryId, TaskCreateDto taskCreateDto) {
        Optional<Task> task = taskRepository.addTask(categoryId, taskCreateDto);

        return task.map(taskMapper::toTaskReadDto);
    }

    @Override
    public void deleteTask(Integer taskId) {
        taskRepository.deleteTask(taskId);
    }


    @Override
    @Transactional
    public Optional<TaskReadDto> editTask(int taskId, TaskUpdateDto taskUpdateDto) {
        Optional<Task> originalTask = taskRepository.getTaskById(taskId);

        if (originalTask.isPresent()) {
            Optional<Task> task = taskRepository.editTask(taskId, taskUpdateDto, originalTask.get());
            return task.map(taskMapper::toTaskReadDto);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<TaskReadDto> toggleComplete(int taskId) {
        Optional<Task> task = taskRepository.toggleComplete(taskId);
        return task.map(taskMapper::toTaskReadDto);
    }
}
