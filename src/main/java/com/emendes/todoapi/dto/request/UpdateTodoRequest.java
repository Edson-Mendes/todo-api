package com.emendes.todoapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

/**
 * Record DTO para receber dados de atualização de Todo.
 */
@Builder
public record UpdateTodoRequest(
    @NotBlank(message = "description must not be blank")
    @Size(min = 2, max = 255, message = "description must contain between {min} and {max} characters long")
    String description,
    @NotNull(message = "concluded must not be null")
    Boolean concluded
) {
}
