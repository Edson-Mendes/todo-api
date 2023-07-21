package com.emendes.todoapi.service;

import com.emendes.todoapi.dto.request.CreateTodoRequest;
import com.emendes.todoapi.dto.request.UpdateTodoRequest;
import com.emendes.todoapi.dto.response.TodoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interface service com as abstrações para manipulação do recurso Todo.
 */
public interface TodoService {

  /**
   * Salva um Todo no sistema.
   *
   * @param createTodoRequest contendo as informações do novo Todo.
   * @return {@link TodoResponse} contendo informações sobre o Todo registrado.
   */
  TodoResponse save(CreateTodoRequest createTodoRequest);

  /**
   * Busca paginada de Todos.
   *
   * @param pageable objeto que contém os parâmetros  da paginação.
   * @return Page of TodoResponse.
   */
  Page<TodoResponse> fetchPageable(Pageable pageable);

  /**
   * Busca Todo por todoId.
   *
   * @param todoId identificador do Todo.
   * @return TodoResponse contendo as informações do Todo encontrado.
   */
  TodoResponse findById(String todoId);

  /**
   * Atualiza uma Todo, dado um identificador da Todo e o DTO contendo as novas informações.
   *
   * @param todoId      identificador da Todo.
   * @param todoRequest que contém as novas informações da Todo.
   */
  void update(String todoId, UpdateTodoRequest todoRequest);

  /**
   * Deleta uma Todo, dado um identificador da Todo (todoId).
   *
   * @param todoId identificador da Todo.
   */
  void delete(String todoId);

}
