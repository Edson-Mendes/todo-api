package com.emendes.todoapi.unit.service;

import com.emendes.todoapi.dto.request.TodoRequest;
import com.emendes.todoapi.dto.response.TodoResponse;
import com.emendes.todoapi.mapper.TodoMapper;
import com.emendes.todoapi.model.Todo;
import com.emendes.todoapi.repository.TodoRepository;
import com.emendes.todoapi.service.impl.TodoServiceImpl;
import com.emendes.todoapi.util.AuthenticationFacade;
import com.emendes.todoapi.util.ContantUtil;
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
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

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

      TodoRequest todoRequest = TodoRequest.builder()
          .description("Fazer tarefa X")
          .build();

      TodoResponse actualTodoResponse = todoService.save(todoRequest);

      BDDMockito.verify(todoMapperMock).toTodo(any());
      BDDMockito.verify(authenticationFacadeMock).getCurrentUser();
      BDDMockito.verify(todoRepositoryMock).insert(any(Todo.class));
      BDDMockito.verify(todoMapperMock).toTodoResponse(any());

      Assertions.assertThat(actualTodoResponse).isNotNull();
      Assertions.assertThat(actualTodoResponse.id()).isNotNull().isEqualTo("fedcba");
      Assertions.assertThat(actualTodoResponse.description()).isNotNull().isEqualTo("Fazer tarefa X");
      Assertions.assertThat(actualTodoResponse.concluded()).isFalse();
      Assertions.assertThat(actualTodoResponse.creationDate()).isNotNull();
      Assertions.assertThat(actualTodoResponse.userId()).isNotNull().isEqualTo("abcdef");
    }

  }

  @Nested
  @DisplayName("Tests for fetchPageable method")
  class FetchPageableMethod {

    @Test
    @DisplayName("fetchPageable must return Page<TodoResponse> when fetch successfully")
    void fetchPageable_MustReturnPageTodoResponse_WhenFetchSuccessfully() {
      Todo todo = TodoFaker.todo();
      BDDMockito.when(authenticationFacadeMock.getCurrentUser())
          .thenReturn(UserFaker.user());
      BDDMockito.when(todoRepositoryMock.findByUserId(any(), eq(ContantUtil.PAGEABLE)))
          .thenReturn(new PageImpl<>(List.of(todo, todo), ContantUtil.PAGEABLE, 2));
      BDDMockito.when(todoMapperMock.toTodoResponse(any()))
          .thenReturn(TodoFaker.todoResponse());

      Page<TodoResponse> actualTodoResponsePage = todoService.fetchPageable(ContantUtil.PAGEABLE);

      BDDMockito.verify(authenticationFacadeMock).getCurrentUser();
      BDDMockito.verify(todoRepositoryMock).findByUserId(any(), eq(ContantUtil.PAGEABLE));
      BDDMockito.verify(todoMapperMock, Mockito.times(2)).toTodoResponse(any());

      Assertions.assertThat(actualTodoResponsePage).isNotNull().hasSize(2);
    }

  }

  @Nested
  @DisplayName("Tests for findById method")
  class FindByIdMethod {

    @Test
    @DisplayName("findById must return TodoResponse when found successfully")
    void findById_MustReturnTodoResponse_WhenFoundSuccessfully() {
      BDDMockito.when(authenticationFacadeMock.getCurrentUser())
          .thenReturn(UserFaker.user());
      BDDMockito.when(todoRepositoryMock.findByIdAndUserId(any(), any()))
          .thenReturn(Optional.of(TodoFaker.todo()));
      BDDMockito.when(todoMapperMock.toTodoResponse(any()))
          .thenReturn(TodoFaker.todoResponse());

      TodoResponse actualTodoResponse = todoService.findById("fdecba");

      BDDMockito.verify(authenticationFacadeMock).getCurrentUser();
      BDDMockito.verify(todoRepositoryMock).findByIdAndUserId(any(), any());
      BDDMockito.verify(todoMapperMock).toTodoResponse(any());

      Assertions.assertThat(actualTodoResponse).isNotNull();
      Assertions.assertThat(actualTodoResponse.id()).isNotNull().isEqualTo("fedcba");
      Assertions.assertThat(actualTodoResponse.description()).isNotNull().isEqualTo("Fazer tarefa X");
      Assertions.assertThat(actualTodoResponse.concluded()).isFalse();
      Assertions.assertThat(actualTodoResponse.creationDate()).isNotNull();
      Assertions.assertThat(actualTodoResponse.userId()).isNotNull().isEqualTo("abcdef");
    }

    @Test
    @DisplayName("findById must throw ResponseStatusException when not found todo with id 'fedcba'")
    void findById_MustThrowResponseStatusException_WhenNotFoundTodoWithIdFEDCBA() {
      BDDMockito.when(authenticationFacadeMock.getCurrentUser())
          .thenReturn(UserFaker.user());
      BDDMockito.when(todoRepositoryMock.findByIdAndUserId(any(), any()))
          .thenReturn(Optional.empty());

      Assertions.assertThatExceptionOfType(ResponseStatusException.class)
          .isThrownBy(() -> todoService.findById("fedcba"))
          .withMessageContaining("Todo not found");

      BDDMockito.verify(authenticationFacadeMock).getCurrentUser();
      BDDMockito.verify(todoRepositoryMock).findByIdAndUserId(any(), any());
    }

  }

}