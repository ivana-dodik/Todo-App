package org.unibl.etf.todo.mapper;

import org.springframework.stereotype.Component;
import org.unibl.etf.todo.domain.Task;
import org.unibl.etf.todo.dto.TaskReadDto;

import java.util.List;

@Component
public class TaskMapper {
    public TaskReadDto toTaskReadDto(Task task) {
        return new TaskReadDto(
                task.getId(),
                task.getName(),
                task.getDue(),
                task.getPriority(),
                task.isCompleted()
        );
    }

    public List<TaskReadDto> toTaskReadDtoList(List<Task> tasks) {
        return tasks.stream().map(this::toTaskReadDto).toList();
    }
}

