package com.emendes.todoapi.service.impl;

import com.emendes.todoapi.dto.request.RegisterUserRequest;
import com.emendes.todoapi.dto.response.UserResponse;
import com.emendes.todoapi.mapper.UserMapper;
import com.emendes.todoapi.model.User;
import com.emendes.todoapi.repository.UserRepository;
import com.emendes.todoapi.service.UserService;
import com.emendes.todoapi.util.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static com.emendes.todoapi.security.SecurityConfig.ROLE_USER;

/**
 * Implementação de {@link UserService}.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final AuthenticationFacade authenticationFacade;

  /**
   * @throws ResponseStatusException caso RegisterUserRequest.password e RegisterUserRequest.confirmPassword não correspondem.
   *                                 Ou caso o email informado já esteja associado a um User.
   */
  @Override
  public UserResponse register(RegisterUserRequest registerUserRequest) {
    if (!registerUserRequest.passwordsMatch()) {
      log.info("password and confirm_password do not match");
      throw new ResponseStatusException(
          HttpStatusCode.valueOf(400),
          "password and confirm_password do not match");
    }
    if (userRepository.existsByEmail(registerUserRequest.email())) {
      log.info("email {} already in use", registerUserRequest.email());
      throw new ResponseStatusException(
          HttpStatusCode.valueOf(409),
          String.format("email %s already in use", registerUserRequest.email()));
    }

    User user = userMapper.toUser(registerUserRequest);

    user.addAuthority(ROLE_USER);
    user.setCreationDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    userRepository.insert(user);

    log.info("user {} registered successfully", user.getEmail());
    return userMapper.toUserResponse(user);
  }

  /**
   * @throws ResponseStatusException caso o id fornecido seja diferente do usuário logado.
   */
  @Override
  public UserResponse findById(String id) {
    log.info("attempt to fetch user with id: {}", id);
    User currentUser = authenticationFacade.getCurrentUser();

    if (!currentUser.getId().equals(id)) {
      log.info("current user is not allowed to fetch other users");
      throw new ResponseStatusException(HttpStatusCode.valueOf(404), "user not found");
    }

    log.info("user found successfully with id: {}", id);
    User user = findUserById(id);
    return userMapper.toUserResponse(user);
  }

  /**
   * Busca no banco de dados um User com o dado id.
   *
   * @throws ResponseStatusException caso não exista um usuário do o dado id.
   */
  private User findUserById(String id) {
    return userRepository.findById(id)
        .orElseThrow(() -> {
          log.info("user not found for id: {}", id);
          return new ResponseStatusException(HttpStatusCode.valueOf(404), "user not found");
        });
  }

}

