package org.unibl.etf.todo.dto;

import org.unibl.etf.todo.domain.Priority;

import java.time.LocalDateTime;

public record TaskUpdateDto(String name, LocalDateTime due, Priority priority, boolean completed) {
}
