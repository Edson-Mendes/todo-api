package com.emendes.todoapi.unit.service;

import com.emendes.todoapi.dto.request.CreateTodoRequest;
import com.emendes.todoapi.dto.response.TodoResponse;
import com.emendes.todoapi.mapper.TodoMapper;
import com.emendes.todoapi.model.Todo;
import com.emendes.todoapi.repository.TodoRepository;
import com.emendes.todoapi.service.impl.TodoServiceImpl;
import com.emendes.todoapi.util.AuthenticationFacade;
import com.emendes.todoapi.util.faker.TodoFaker;
import com.emendes.todoapi.util.faker.UserFaker;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;

/**
 * Unit tests para o record dto {@link TodoServiceImpl}
 */
@ExtendWith(SpringExtension.class)
@DisplayName("Unit tests for TodoServiceImpl")
class TodoServiceImplTest {

  @InjectMocks
  private TodoServiceImpl todoService;
  @Mock
  private TodoMapper todoMapperMock;
  @Mock
  private TodoRepository todoRepositoryMock;
  @Mock
  private AuthenticationFacade authenticationFacadeMock;

  @Nested
  @DisplayName("Tests for save method")
  class SaveMethod {

    @Test
    @DisplayName("save must return TodoResponse when save successfully")
    void save_MustReturnTodoResponse_WhenSaveSuccessfully() {
      BDDMockito.when(todoMapperMock.toTodo(any()))
          .thenReturn(TodoFaker.partialTodo());
      BDDMockito.when(authenticationFacadeMock.getCurrentUser())
          .thenReturn(UserFaker.user());
      BDDMockito.when(todoRepositoryMock.insert(any(Todo.class)))
          .thenReturn(TodoFaker.todo());
      BDDMockito.when(todoMapperMock.toTodoResponse(any()))
          .thenReturn(TodoFaker.todoResponse());

      CreateTodoRequest createTodoRequest = CreateTodoRequest.builder()
          .description("Fazer tarefa X")
          .build();

      TodoResponse actualTodoResponse = todoService.save(createTodoRequest);

      Assertions.assertThat(actualTodoResponse).isNotNull();
      Assertions.assertThat(actualTodoResponse.id()).isNotNull().isEqualTo("fedcba");
      Assertions.assertThat(actualTodoResponse.description()).isNotNull().isEqualTo("Fazer tarefa X");
      Assertions.assertThat(actualTodoResponse.concluded()).isFalse();
      Assertions.assertThat(actualTodoResponse.creationDate()).isNotNull();
      Assertions.assertThat(actualTodoResponse.userId()).isNotNull().isEqualTo("abcdef");
    }

  }

}