package com.emendes.todoapi.unit.mapper;

import com.emendes.todoapi.dto.request.CreateTodoRequest;
import com.emendes.todoapi.dto.response.TodoResponse;
import com.emendes.todoapi.mapper.impl.TodoMapperImpl;
import com.emendes.todoapi.model.Todo;
import com.emendes.todoapi.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Unit tests para o record dto {@link TodoMapperImpl}
 */
@DisplayName("Unit tests for TodoMapperImpl")
class TodoMapperImplTest {

  private final TodoMapperImpl todoMapper = new TodoMapperImpl();

  @Test
  @DisplayName("toTodo must return Todo when map successfully")
  void toTodo_MustReturnTodo_WhenMapSuccessfully() {
    CreateTodoRequest todoRequest = CreateTodoRequest.builder()
        .description("Fazer tarefa x")
        .build();

    Todo actualTodo = todoMapper.toTodo(todoRequest);

    Assertions.assertThat(actualTodo).isNotNull();
    Assertions.assertThat(actualTodo.getDescription()).isNotNull().isEqualTo("Fazer tarefa x");
    Assertions.assertThat(actualTodo.getId()).isNull();
    Assertions.assertThat(actualTodo.getCreationDate()).isNull();
    Assertions.assertThat(actualTodo.getUser()).isNull();
  }

  @Test
  @DisplayName("toTodo must throw IllegalArgumentException when todoRequest param is null")
  void toTodo_mustThrowIllegalArgumentException_WhenTodoRequestParamIsNull() {
    Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> todoMapper.toTodo(null))
        .withMessageContaining("TodoRequest must not be null");
  }

  @Test
  @DisplayName("toTodoResponse must return Todo when map successfully")
  void toTodoResponse_MustReturnTodo_WhenMapSuccessfully() {
    Todo todo = Todo.builder()
        .id("aaaabbbbcccc")
        .description("Fazer tarefa x")
        .concluded(false)
        .creationDate(LocalDateTime.parse("2023-07-05T10:30:00").truncatedTo(ChronoUnit.SECONDS))
        .user(User.builder().id("asdmpimuoibvuysdubvausbdinv").build())
        .build();

    TodoResponse actualTodoResponse = todoMapper.toTodoResponse(todo);

    Assertions.assertThat(actualTodoResponse).isNotNull();
    Assertions.assertThat(actualTodoResponse.id()).isNotNull().isEqualTo("aaaabbbbcccc");
    Assertions.assertThat(actualTodoResponse.description()).isNotNull().isEqualTo("Fazer tarefa x");
    Assertions.assertThat(actualTodoResponse.concluded()).isFalse();
    Assertions.assertThat(actualTodoResponse.creationDate()).isNotNull().isEqualTo("2023-07-05T10:30:00");
    Assertions.assertThat(actualTodoResponse.userId()).isNotNull().isEqualTo("asdmpimuoibvuysdubvausbdinv");
  }

  @Test
  @DisplayName("toTodoResponse must throw IllegalArgumentException when todoRequest param is null")
  void toTodoResponse_mustThrowIllegalArgumentException_WhenTodoRequestParamIsNull() {
    Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> todoMapper.toTodoResponse(null))
        .withMessageContaining("Todo must not be null");
  }

}