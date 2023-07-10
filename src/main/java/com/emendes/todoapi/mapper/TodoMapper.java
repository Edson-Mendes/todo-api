package com.emendes.todoapi.mapper;

import com.emendes.todoapi.dto.request.CreateTodoRequest;
import com.emendes.todoapi.dto.response.TodoResponse;
import com.emendes.todoapi.model.Todo;

/**
 * Interface component que contém as abstrações de mapeamento de DTOs para o document Todo e vice-versa.
 */
public interface TodoMapper {

  /**
   * Mapeia o DTO CreateTodoRequest para o document Todo.<br>
   * todoRequest não deve ser null.
   *
   * @param todoRequest que será mapeado para Todo
   * @return {@link Todo} contendo as informações que estavam em todoRequest.
   */
  Todo toTodo(CreateTodoRequest todoRequest);

  /**
   * Mapeia um docment Todo para o DTO TodoResponse.<br>
   * Todo não deve ser null.
   *
   * @param todo que será mapeado para TodoResponse.
   * @return {@link TodoResponse} contendo informações que estavam em Todo.
   */
  TodoResponse toTodoResponse(Todo todo);

}
