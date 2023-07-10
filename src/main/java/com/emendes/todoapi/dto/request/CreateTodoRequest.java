package com.emendes.todoapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Record DTO para receber dados de criação de Todo no corpo da requisição.
 */
public record CreateTodoRequest(
    @NotBlank
    @Size(min = 2, max = 255, message = "description must contain between {min} and {max} characters long")
    String description
) {
}
