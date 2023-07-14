package com.emendes.todoapi.controller;

import com.emendes.todoapi.dto.request.TodoRequest;
import com.emendes.todoapi.dto.response.TodoResponse;
import com.emendes.todoapi.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * Controller o qual é mapeado as requisições de /api/todos.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/todos")
public class TodoController {

  private final TodoService todoService;

  /**
   * Trata requisição POST /api/todos.
   *
   * @param todoRequest DTO que contém as informações do Todo a ser salvo.
   */
  @PostMapping
  public ResponseEntity<TodoResponse> save(
      @RequestBody @Valid TodoRequest todoRequest, UriComponentsBuilder uriBuilder) {
    TodoResponse todoResponse = todoService.save(todoRequest);

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
  public ResponseEntity<TodoResponse> findById(@PathVariable(name = "id") String todoId) {
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
      @PathVariable(name = "id") String todoId, @RequestBody @Valid TodoRequest todoRequest) {
    todoService.update(todoId, todoRequest);
    return ResponseEntity.noContent().build();
  }

}
