package org.unibl.etf.todo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public final class Task {
    private final int id;
    private String name;
    private LocalDateTime due;
    private Priority priority;
    private boolean completed;
}
