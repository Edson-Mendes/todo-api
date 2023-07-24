package com.emendes.todoapi.util.faker;

import com.emendes.todoapi.dto.request.RegisterUserRequest;
import com.emendes.todoapi.dto.response.UserResponse;
import com.emendes.todoapi.model.User;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;

/**
 * Classe auxiliar com fake document Usere fake DTOs relacionados a User para uso em testes.
 */
public class UserFaker {

  private UserFaker() {}

  /**
   * Retorna um record dto RegisterUserRequest para ser usado em testes.
   */
  public static RegisterUserRequest registerUserRequest() {
    return RegisterUserRequest.builder()
        .name("Lorem Ipsum")
        .email("lorem@email.com")
        .password("1234567890")
        .confirmPassword("1234567890")
        .build();
  }

  /**
   * Retorna um document User com todos os campos para ser usado em testes.
   */
  public static User user() {
    return User.builder()
        .id("abcdef")
        .name("Lorem Ipsum")
        .email("lorem@email.com")
        .password("1234567890")
        .creationDate(LocalDateTime.parse("2023-07-05T10:30:00").truncatedTo(ChronoUnit.SECONDS))
        .authorities(Set.of("ROLE_USER"))
        .build();
  }

  /**
   * Retorna um document User sem o campo id, authority e creationDate para ser usado em testes.
   */
  public static User partialUser() {
    return User.builder()
        .name("Lorem Ipsum")
        .email("lorem@email.com")
        .password("1234567890")
        .build();
  }

  /**
   * Retorna userResponse com todos os campos. Para ser usado em testes.
   */
  public static UserResponse userResponse() {
    return UserResponse.builder()
        .id("abcdef")
        .name("Lorem Ipsum")
        .email("lorem@email.com")
        .uriImage(ImageFaker.uri())
        .creationDate(LocalDateTime.parse("2023-07-05T10:30:00").truncatedTo(ChronoUnit.SECONDS))
        .build();
  }
}
