package com.emendes.todoapi.mapper.impl;

import com.emendes.todoapi.dto.request.CreateTodoRequest;
import com.emendes.todoapi.dto.response.TodoResponse;
import com.emendes.todoapi.mapper.TodoMapper;
import com.emendes.todoapi.model.Todo;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Implementação de {@link TodoMapper}.
 */
@Component
public class TodoMapperImpl implements TodoMapper {

  @Override
  public Todo toTodo(CreateTodoRequest createTodoRequest) {
    Assert.notNull(createTodoRequest, "CreateTodoRequest must not be null");

    return Todo.builder()
        .description(createTodoRequest.description())
        .build();
  }

  @Override
  public TodoResponse toTodoResponse(Todo todo) {
    Assert.notNull(todo, "Todo must not be null");

    return TodoResponse.builder()
        .id(todo.getId())
        .description(todo.getDescription())
        .concluded(todo.isConcluded())
        .creationDate(todo.getCreationDate())
        .userId(todo.getUser().getId())
        .build();
  }

}
