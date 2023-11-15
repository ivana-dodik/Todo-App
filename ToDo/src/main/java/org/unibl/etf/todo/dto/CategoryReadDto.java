package org.unibl.etf.todo.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryReadDto {
    private int id;
    private String name;
    private List<TaskReadDto> tasks;
}
