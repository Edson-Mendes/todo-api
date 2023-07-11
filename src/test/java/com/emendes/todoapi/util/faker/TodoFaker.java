package com.emendes.todoapi.util.faker;

import com.emendes.todoapi.dto.response.TodoResponse;
import com.emendes.todoapi.model.Todo;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Classe auxiliar com fake document Todo e fake DTOs relacionados a Todo para uso em testes.
 */
public class TodoFaker {

  private TodoFaker() {
  }

  /**
   * Retorna um document Todo sem o campo id, user, creationDate para ser usado em testes.
   */
  public static Todo partialTodo() {
    return Todo.builder()
        .description("Fazer tarefa X")
        .build();
  }

  /**
   * Retorna um document Todo com todos os campos para ser usado em testes.
   */
  public static Todo todo() {
    return Todo.builder()
        .id("fedcba")
        .description("Fazer tarefa X")
        .concluded(false)
        .creationDate(LocalDateTime.parse("2023-07-13T08:40:00").truncatedTo(ChronoUnit.SECONDS))
        .user(UserFaker.user())
        .build();
  }

  /**
   * Retorna todoResponse com todos os campos. Para ser usado em testes.
   */
  public static TodoResponse todoResponse() {
    return TodoResponse.builder()
        .id("fedcba")
        .description("Fazer tarefa X")
        .concluded(false)
        .creationDate(LocalDateTime.parse("2023-07-13T08:40:00").truncatedTo(ChronoUnit.SECONDS))
        .userId("abcdef")
        .build();
  }
}
