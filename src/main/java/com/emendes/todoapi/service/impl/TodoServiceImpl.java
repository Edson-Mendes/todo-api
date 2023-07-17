package com.emendes.todoapi.service.impl;

import com.emendes.todoapi.dto.request.TodoRequest;
import com.emendes.todoapi.dto.response.TodoResponse;
import com.emendes.todoapi.mapper.TodoMapper;
import com.emendes.todoapi.model.Todo;
import com.emendes.todoapi.model.User;
import com.emendes.todoapi.repository.TodoRepository;
import com.emendes.todoapi.service.TodoService;
import com.emendes.todoapi.util.component.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Implementação de {@link TodoService}.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class TodoServiceImpl implements TodoService {

  private final TodoMapper todoMapper;
  private final AuthenticationFacade authenticationFacade;
  private final TodoRepository todoRepository;

  @Override
  public TodoResponse save(TodoRequest todoRequest) {
    log.info("attempt to save todo");

    Todo todo = todoMapper.toTodo(todoRequest);
    User currentUser = authenticationFacade.getCurrentUser();

    todo.setConcluded(false);
    todo.setCreationDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    todo.setUser(currentUser);

    todoRepository.insert(todo);

    log.info("todo with id: {} saved successfully", todo.getId());
    return todoMapper.toTodoResponse(todo);
  }

  @Override
  public Page<TodoResponse> fetchPageable(Pageable pageable) {
    log.info("attempt to fetch page: {} with size: {}", pageable.getPageNumber(), pageable.getPageSize());

    User currentUser = authenticationFacade.getCurrentUser();
    Page<Todo> todoPage = todoRepository.findByUserId(currentUser.getId(), pageable);

    return todoPage.map(todoMapper::toTodoResponse);
  }

  @Override
  public TodoResponse findById(String todoId) {
    log.info("attempt to find todo with id: {}", todoId);
    Todo todo = findTodoById(todoId);

    log.info("Todo with id: {} found successfully", todoId);
    return todoMapper.toTodoResponse(todo);
  }

  @Override
  public void update(String todoId, TodoRequest todoRequest) {
    log.info("attempt to update todo with id: {}", todoId);
    Todo todo = findTodoById(todoId);

    todo.setDescription(todoRequest.description());
    todoRepository.save(todo);

    log.info("todo with id: {} updated successful", todoId);
  }

  /**
   * Busca Todo por todoId e userId.
   *
   * @param todoId identificador do Todo.
   * @return todo encontrando para o dado todoId.
   * @throws ResponseStatusException caso Todo não seja encontrado.
   */
  private Todo findTodoById(String todoId) {
    String userId = authenticationFacade.getCurrentUser().getId();

    return todoRepository.findByIdAndUserId(todoId, userId)
        .orElseThrow(() -> {
          log.info("todo not found with id: {}", todoId);
          return new ResponseStatusException(HttpStatusCode.valueOf(404), "Todo not found");
        });
  }

}
