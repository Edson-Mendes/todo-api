package com.emendes.todoapi.service.impl;

import com.emendes.todoapi.dto.request.CreateTodoRequest;
import com.emendes.todoapi.dto.response.TodoResponse;
import com.emendes.todoapi.mapper.TodoMapper;
import com.emendes.todoapi.model.Todo;
import com.emendes.todoapi.model.User;
import com.emendes.todoapi.repository.TodoRepository;
import com.emendes.todoapi.service.TodoService;
import com.emendes.todoapi.util.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
  public TodoResponse save(CreateTodoRequest createTodoRequest) {
    log.info("attempt to save todo");

    Todo todo = todoMapper.toTodo(createTodoRequest);
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

}
