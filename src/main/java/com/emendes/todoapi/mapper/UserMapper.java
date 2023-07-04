package com.emendes.todoapi.mapper;

import com.emendes.todoapi.dto.request.RegisterUserRequest;
import com.emendes.todoapi.dto.response.UserResponse;
import com.emendes.todoapi.model.User;

/**
 * Interface component que contém as abstrações de mapeamento de DTOs para o document User e vice-versa.
 */
public interface UserMapper {

  /**
   * Mapeia o DTO RegisterUserRequest para o document User.<br>
   * UserRequest não deve ser null.
   *
   * @param userRequest que será mapeado para User
   * @return {@link User} contendo as informações que estavam em userRequest.
   */
  User toUser(RegisterUserRequest userRequest);

  /**
   * Mapeia um docment User para o DTO UserResponse.<br>
   * User não deve ser null.
   *
   * @param user que será mapeado para UserResponse.
   * @return {@link UserResponse} contendo informações que estavam em User.
   */
  UserResponse toUserResponse(User user);

}
