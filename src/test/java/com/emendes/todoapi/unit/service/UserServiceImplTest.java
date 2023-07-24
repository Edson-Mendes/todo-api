package com.emendes.todoapi.unit.service;

import com.emendes.todoapi.dto.request.RegisterUserRequest;
import com.emendes.todoapi.dto.response.UserResponse;
import com.emendes.todoapi.mapper.UserMapper;
import com.emendes.todoapi.model.User;
import com.emendes.todoapi.repository.UserRepository;
import com.emendes.todoapi.service.ImageService;
import com.emendes.todoapi.service.impl.UserServiceImpl;
import com.emendes.todoapi.util.component.AuthenticationFacade;
import com.emendes.todoapi.util.faker.ImageFaker;
import com.emendes.todoapi.util.faker.UserFaker;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.Optional;

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
  private ImageService imageServiceMock;
  @Mock
  private UserRepository userRepositoryMock;
  @Mock
  private AuthenticationFacade authenticationFacadeMock;

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
      BDDMockito.when(imageServiceMock.store(any()))
          .thenReturn(ImageFaker.uri());

      MultipartFile file = new MockMultipartFile(
          "user_image", "user.jpg", MediaType.IMAGE_JPEG_VALUE, "image".getBytes());

      RegisterUserRequest request = UserFaker.registerUserRequest();

      UserResponse actualUserResponse = userService.register(request, file);

      BDDMockito.verify(userMapperMock).toUser(any());
      BDDMockito.verify(userMapperMock).toUserResponse(any());
      BDDMockito.verify(userRepositoryMock).insert(any(User.class));
      BDDMockito.verify(userRepositoryMock).existsByEmail(any());
      BDDMockito.verify(passwordEncoderMock).encode(any());
      BDDMockito.verify(imageServiceMock).store(any());

      Assertions.assertThat(actualUserResponse).isNotNull();
      Assertions.assertThat(actualUserResponse.id()).isNotNull();
      Assertions.assertThat(actualUserResponse.name()).isNotNull().isEqualTo("Lorem Ipsum");
      Assertions.assertThat(actualUserResponse.email()).isNotNull().isEqualTo("lorem@email.com");
      Assertions.assertThat(actualUserResponse.uriImage()).isNotNull()
          .isEqualByComparingTo(URI.create("/api/images/1234567890"));
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

      MultipartFile file = new MockMultipartFile(
          "user_image", "user.jpg", MediaType.IMAGE_JPEG_VALUE, "image".getBytes());

      Assertions.assertThatExceptionOfType(ResponseStatusException.class)
          .isThrownBy(() -> userService.register(request, file))
          .withMessageContaining("password and confirm_password do not match");
    }

    @Test
    @DisplayName("register must throw ResponseStatusException when already exists User with given email")
    void register_MustThrowResponseStatusException_WhenAlreadyExistsUserWithGivenEmail() {
      BDDMockito.when(userRepositoryMock.existsByEmail(any()))
          .thenReturn(true);
      RegisterUserRequest request = UserFaker.registerUserRequest();

      MultipartFile file = new MockMultipartFile(
          "user_image", "user.jpg", MediaType.IMAGE_JPEG_VALUE, "image".getBytes());

      Assertions.assertThatExceptionOfType(ResponseStatusException.class)
          .isThrownBy(() -> userService.register(request, file))
          .withMessageContaining("email lorem@email.com already in use");

      BDDMockito.verify(userRepositoryMock).existsByEmail(any());
    }

  }

  @Nested
  @DisplayName("Tests for findById method")
  class FindByIdMethod {

    @Test
    @DisplayName("findById must return UserResponse when found user successfully")
    void findById_MustReturnUserResponse_WhenFoundUserSuccessfully() {
      User userFaker = UserFaker.user();
      BDDMockito.when(authenticationFacadeMock.getCurrentUser())
          .thenReturn(userFaker);
      BDDMockito.when(userRepositoryMock.findById(any()))
          .thenReturn(Optional.of(userFaker));
      BDDMockito.when(userMapperMock.toUserResponse(any()))
          .thenReturn(UserFaker.userResponse());

      UserResponse actualUserResponse = userService.findById("abcdef");

      BDDMockito.verify(authenticationFacadeMock).getCurrentUser();
      BDDMockito.verify(userRepositoryMock).findById(any());
      BDDMockito.verify(userMapperMock).toUserResponse(any());

      Assertions.assertThat(actualUserResponse).isNotNull();
      Assertions.assertThat(actualUserResponse.id()).isNotNull().isEqualTo("abcdef");
      Assertions.assertThat(actualUserResponse.name()).isNotNull().isEqualTo("Lorem Ipsum");
      Assertions.assertThat(actualUserResponse.email()).isNotNull().isEqualTo("lorem@email.com");
    }

    @Test
    @DisplayName("findById must throw ResponseStatusException when provided id is not equals to current user id")
    void findById_MustThrowResponseStatusException_WhenProvidedIdIsNotEqualsToCurrentUserId() {
      BDDMockito.when(authenticationFacadeMock.getCurrentUser())
          .thenReturn(UserFaker.user());

      Assertions.assertThatExceptionOfType(ResponseStatusException.class)
              .isThrownBy(() -> userService.findById("fedcba"))
          .withMessageContaining("user not found");

      BDDMockito.verify(authenticationFacadeMock).getCurrentUser();
    }

    @Test
    @DisplayName("findById must throw ResponseStatusException when user not found")
    void findById_MustThrowResponseStatusException_WhenUserNotFound() {
      BDDMockito.when(authenticationFacadeMock.getCurrentUser())
          .thenReturn(UserFaker.user());
      BDDMockito.when(userRepositoryMock.findById(any()))
          .thenReturn(Optional.empty());

      Assertions.assertThatExceptionOfType(ResponseStatusException.class)
          .isThrownBy(() -> userService.findById("abcdef"))
          .withMessageContaining("user not found");

      BDDMockito.verify(authenticationFacadeMock).getCurrentUser();
      BDDMockito.verify(userRepositoryMock).findById(any());
    }

  }

}