package com.emendes.todoapi.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * Record DTO para enviar informações do Todo para o cliente no corpo da resposta.
 */
@Builder
public record TodoResponse(
    String id,
    String description,
    boolean concluded,
    @JsonProperty("creation_date")
    LocalDateTime creationDate,
    @JsonProperty("user_id")
    String userId
) {
}
