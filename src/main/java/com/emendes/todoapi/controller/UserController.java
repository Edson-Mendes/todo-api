package com.emendes.todoapi.controller;

import com.emendes.todoapi.dto.request.RegisterUserRequest;
import com.emendes.todoapi.dto.response.UserResponse;
import com.emendes.todoapi.service.UserService;
import com.emendes.todoapi.util.annotation.IdValidation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * Controller o qual é mapeado as requisições de /api/users.
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  /**
   * Trata requisição POST /api/users.
   *
   * @param userRequest DTO que contém as informações do User a ser registrado.
   */
  @PostMapping
  public ResponseEntity<UserResponse> register(
      @RequestBody @Valid RegisterUserRequest userRequest, UriComponentsBuilder uriBuilder) {
    UserResponse userResponse = userService.register(userRequest);
    URI uri = uriBuilder.path("/api/users/{id}").build(userResponse.id());

    return ResponseEntity.created(uri).body(userResponse);
  }

  /**
   * Trata requisição GET /api/users/{id}.
   *
   * @param id identificador do usuário a ser buscado.
   */
  @GetMapping("/{id}")
  public ResponseEntity<UserResponse> findById(@PathVariable(name = "id") @IdValidation String id) {
    return ResponseEntity.ok(userService.findById(id));
  }

}
