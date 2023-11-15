package org.unibl.etf.todo.dto;

import lombok.Data;
import org.unibl.etf.todo.domain.Priority;

import java.time.LocalDateTime;

@Data
public class TaskCreateDto {
    private String name;
    private Priority priority;
    private LocalDateTime due;
}
