package com.emendes.todoapi.controller;

import com.emendes.todoapi.dto.request.CreateTodoRequest;
import com.emendes.todoapi.dto.request.UpdateTodoRequest;
import com.emendes.todoapi.dto.response.TodoResponse;
import com.emendes.todoapi.service.TodoService;
import com.emendes.todoapi.util.annotation.IdValidation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * Controller o qual é mapeado as requisições de /api/todos.
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/todos")
public class TodoController {

  private final TodoService todoService;

  /**
   * Trata requisição POST /api/todos.
   *
   * @param createTodoRequest DTO que contém as informações do Todo a ser salvo.
   */
  @PostMapping
  public ResponseEntity<TodoResponse> save(
      @RequestBody @Valid CreateTodoRequest createTodoRequest, UriComponentsBuilder uriBuilder) {
    TodoResponse todoResponse = todoService.save(createTodoRequest);

    URI uri = uriBuilder.path("/api/todos/{id}").build(todoResponse.id());

    return ResponseEntity.created(uri).body(todoResponse);
  }

  /**
   * Trata requisição GET /api/todos.
   *
   * @param pageable objeto que contém os parâmetros da paginação.
   */
  @GetMapping
  public ResponseEntity<Page<TodoResponse>> fetchPageable(@PageableDefault Pageable pageable) {
    return ResponseEntity.ok(todoService.fetchPageable(pageable));
  }

  /**
   * Trata requisição GET /api/todos/{id}.
   *
   * @param todoId identificador da Todo a ser buscado.
   */
  @GetMapping("/{id}")
  public ResponseEntity<TodoResponse> findById(@PathVariable(name = "id") @IdValidation String todoId) {
    return ResponseEntity.ok(todoService.findById(todoId));
  }

  /**
   * Trata requisição PUT /api/todos/{id}
   *
   * @param todoId      identificador da Todo.
   * @param todoRequest contendo as novas informações da Todo.
   */
  @PutMapping("/{id}")
  public ResponseEntity<Void> update(
      @PathVariable(name = "id") @IdValidation String todoId, @RequestBody @Valid UpdateTodoRequest todoRequest) {
    todoService.update(todoId, todoRequest);
    return ResponseEntity.noContent().build();
  }

  /**
   * Trata requisição DELETE /api/todos/{id}
   *
   * @param todoId identificador da Todo.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(
      @PathVariable(name = "id") @IdValidation String todoId) {
    todoService.delete(todoId);
    return ResponseEntity.noContent().build();
  }

}
