package com.emendes.todoapi.service;

import com.emendes.todoapi.dto.request.RegisterUserRequest;
import com.emendes.todoapi.dto.response.UserResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * Interface service com as abstrações para manipulação do recurso User.
 */
public interface UserService {

  /**
   * Registra um User no sistema.
   *
   * @param registerUserRequest contendo as informações do novo User.
   * @return {@link UserResponse} contendo informações sobre o usuário registrado.
   */
  UserResponse register(RegisterUserRequest registerUserRequest, MultipartFile file);

  /**
   * Busca um usuário por id.
   *
   * @param id do User a ser buscado
   * @return UserResponse contendo as informações do usuário encontrado.
   */
  UserResponse findById(String id);

}
