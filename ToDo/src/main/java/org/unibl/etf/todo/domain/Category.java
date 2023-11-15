package org.unibl.etf.todo.domain;

import java.util.List;

public record Category(int id, String name, List<Task> tasks) {
}
