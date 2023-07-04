package com.emendes.todoapi.controller;

import com.emendes.todoapi.dto.request.RegisterUserRequest;
import com.emendes.todoapi.dto.response.UserResponse;
import com.emendes.todoapi.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * Controller o qual é mapeado as requisições de /api/users.
 */
@Slf4j
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

}
