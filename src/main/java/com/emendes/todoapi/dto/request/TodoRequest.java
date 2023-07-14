package com.emendes.todoapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

/**
 * Record DTO para receber dados de criação de Todo no corpo da requisição.
 */
@Builder
public record TodoRequest(
    @NotBlank(message = "description must not be blank")
    @Size(min = 2, max = 255, message = "description must contain between {min} and {max} characters long")
    String description
) {
}
