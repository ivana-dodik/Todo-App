package org.unibl.etf.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.unibl.etf.todo.domain.Priority;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TaskReadDto {
    private int id;
    private String name;
    private LocalDateTime due;
    private Priority priority;
    private boolean completed;
}
