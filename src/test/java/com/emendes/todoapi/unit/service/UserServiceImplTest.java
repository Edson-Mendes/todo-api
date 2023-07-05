package com.emendes.todoapi.unit.service;

import com.emendes.todoapi.dto.request.RegisterUserRequest;
import com.emendes.todoapi.dto.response.UserResponse;
import com.emendes.todoapi.mapper.UserMapper;
import com.emendes.todoapi.model.User;
import com.emendes.todoapi.repository.UserRepository;
import com.emendes.todoapi.service.impl.UserServiceImpl;
import com.emendes.todoapi.util.faker.UserFaker;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.ArgumentMatchers.any;

/**
 * Unit tests para o record dto {@link UserServiceImpl}
 */
@ExtendWith(SpringExtension.class)
@DisplayName("Unit tests for UserServiceImpl")
class UserServiceImplTest {

  @InjectMocks
  private UserServiceImpl userService;
  @Mock
  private UserMapper userMapperMock;
  @Mock
  private PasswordEncoder passwordEncoderMock;
  @Mock
  private UserRepository userRepositoryMock;

  @Nested
  @DisplayName("Tests for register method")
  class RegisterMethod {

    @Test
    @DisplayName("register must return UserResponse when register successfully")
    void register_MustReturnUserResponse_WhenRegisterSuccessfully() {
      BDDMockito.when(userMapperMock.toUser(any()))
          .thenReturn(UserFaker.partialUser());
      BDDMockito.when(userRepositoryMock.insert(any(User.class)))
          .thenReturn(UserFaker.user());
      BDDMockito.when(passwordEncoderMock.encode(any()))
          .thenReturn("encodedPassword");
      BDDMockito.when(userMapperMock.toUserResponse(any()))
          .thenReturn(UserFaker.userResponse());

      RegisterUserRequest request = UserFaker.registerUserRequest();

      UserResponse actualUserResponse = userService.register(request);

      BDDMockito.verify(userMapperMock).toUser(any());
      BDDMockito.verify(userMapperMock).toUserResponse(any());
      BDDMockito.verify(userRepositoryMock).insert(any(User.class));
      BDDMockito.verify(userRepositoryMock).existsByEmail(any());
      BDDMockito.verify(passwordEncoderMock).encode(any());

      Assertions.assertThat(actualUserResponse).isNotNull();
      Assertions.assertThat(actualUserResponse.id()).isNotNull();
      Assertions.assertThat(actualUserResponse.name()).isNotNull().isEqualTo("Lorem Ipsum");
      Assertions.assertThat(actualUserResponse.email()).isNotNull().isEqualTo("lorem@email.com");
      Assertions.assertThat(actualUserResponse.creationDate()).isNotNull();
    }

    @Test
    @DisplayName("register must throw ResponseStatusException when password and confirmPassword does not match")
    void register_MustThrowResponseStatusException_WhenPasswordAndConfirmPasswordDoesNotMatch() {
      RegisterUserRequest request = RegisterUserRequest.builder()
          .name("Lorem Ipsum")
          .email("lorem@email.com")
          .password("12354467890")
          .confirmPassword("1235446789")
          .build();

      Assertions.assertThatExceptionOfType(ResponseStatusException.class)
          .isThrownBy(() -> userService.register(request))
          .withMessageContaining("password and confirm_password do not match");
    }

    @Test
    @DisplayName("register must throw ResponseStatusException when already exists User with given email")
    void register_MustThrowResponseStatusException_WhenAlreadyExistsUserWithGivenEmail() {
      BDDMockito.when(userRepositoryMock.existsByEmail(any()))
          .thenReturn(true);
      RegisterUserRequest request = UserFaker.registerUserRequest();

      Assertions.assertThatExceptionOfType(ResponseStatusException.class)
          .isThrownBy(() -> userService.register(request))
          .withMessageContaining("email lorem@email.com already in use");

      BDDMockito.verify(userRepositoryMock).existsByEmail(any());
    }

  }

}