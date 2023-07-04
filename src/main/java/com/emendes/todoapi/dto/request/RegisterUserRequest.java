package com.emendes.todoapi.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Record DTO para receber dados de criação de User no corpo da requisição.
 */
public record RegisterUserRequest(
    @NotBlank(message = "name must not be blank")
    @Size(min = 2, max = 100, message = "name must contain between {min} and {max} characters long")
    String name,
    @NotBlank(message = "email must not be blank")
    @Size(max = 150, message = "email must contain max {max} characters long")
    @Email(message = "must be a well formed email")
    String email,
    @NotBlank(message = "password must not be blank")
    @Size(min = 8, max = 30, message = "password must contain between {min} and {max} characters long")
    String password,
    @NotBlank(message = "confirm_password must not be blank")
    @JsonProperty("confirm_password")
    String confirmPassword
) {

  /**
   * Verifica se password e confirmPassword são iguais.
   *
   * @return true, se password e confirmPassword forem iguais, false caso contrário.
   */
  public boolean passwordsMatch() {
    if (password == null || confirmPassword == null) return false;
    return password.equals(confirmPassword);
  }

}
