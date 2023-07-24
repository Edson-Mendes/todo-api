package com.emendes.todoapi.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.net.URI;
import java.time.LocalDateTime;

/**
 * Record DTO para enviar informações do User para o cliente no corpo da resposta.
 */
@Builder
public record UserResponse(
    String id,
    String email,
    String name,
    @JsonProperty("uri_image")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    URI uriImage,
    @JsonProperty("creation_date")
    LocalDateTime creationDate
) {
}
