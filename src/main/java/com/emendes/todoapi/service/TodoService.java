package com.emendes.todoapi.service;

import com.emendes.todoapi.dto.request.CreateTodoRequest;
import com.emendes.todoapi.dto.response.TodoResponse;

/**
 * Interface service com as abstrações para manipulação do recurso Todo.
 */
public interface TodoService {

  TodoResponse save(CreateTodoRequest createTodoRequest);

}
