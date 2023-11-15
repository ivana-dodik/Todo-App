package org.unibl.etf.todo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.todo.domain.Priority;
import org.unibl.etf.todo.dto.TaskCreateDto;
import org.unibl.etf.todo.dto.TaskReadDto;
import org.unibl.etf.todo.dto.TaskUpdateDto;
import org.unibl.etf.todo.service.TaskService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/categories/{categoryId}/tasks")
    public ResponseEntity<List<TaskReadDto>> getTasks(@PathVariable("categoryId") Integer categoryId) {
        return ResponseEntity.ok(taskService.getTasks(categoryId));
    }

    @PostMapping("/categories/{categoryId}/tasks")
    public ResponseEntity<TaskReadDto> addTasks(
            @PathVariable("categoryId") Integer categoryId, @RequestBody TaskCreateDto taskCreateDto) {
        if (taskCreateDto.getPriority() == null) {
            taskCreateDto.setPriority(Priority.valueOf("DEFAULT"));
        }
        Optional<TaskReadDto> task = taskService.addTask(categoryId, taskCreateDto);

        return task.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable("taskId") Integer taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/tasks/{taskId}")
    public ResponseEntity<TaskReadDto> editTask(@PathVariable("taskId") Integer taskId,
                                                @RequestBody TaskUpdateDto taskUpdateDto) {
        Optional<TaskReadDto> taskReadDto = taskService.editTask(taskId, taskUpdateDto);

        return taskReadDto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/tasks/{taskId}/toggle-complete")
    public ResponseEntity<TaskReadDto> toggleComplete(@PathVariable("taskId") Integer taskId) {
        Optional<TaskReadDto> task = taskService.toggleComplete(taskId);

        if (task.isPresent()) {
            return ResponseEntity.of(task);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
