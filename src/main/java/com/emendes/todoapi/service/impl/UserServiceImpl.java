package com.emendes.todoapi.service.impl;

import com.emendes.todoapi.dto.request.RegisterUserRequest;
import com.emendes.todoapi.dto.response.UserResponse;
import com.emendes.todoapi.mapper.UserMapper;
import com.emendes.todoapi.model.User;
import com.emendes.todoapi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * Implementação de {@link UserService}.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private final UserMapper userMapper;

  /**
   * @throws ResponseStatusException se RegisterUserRequest.password e RegisterUserRequest.confirmPassword
   *                                 não corresponderem.
   */
  @Override
  public UserResponse register(RegisterUserRequest registerUserRequest) {
    if (!registerUserRequest.passwordsMatch()) {
      throw new ResponseStatusException(
          HttpStatusCode.valueOf(400),
          "password and confirm_password do not match");
    }

    User user = userMapper.toUser(registerUserRequest);

    // TODO: email deve ser único.
    // TODO: add user's authority.
    // TODO: add creationDate.
    // TODO: encryptar o password.

    // TODO: Persistir o document.

    return userMapper.toUserResponse(user);
  }

}
