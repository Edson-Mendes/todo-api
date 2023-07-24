package com.emendes.todoapi.unit.mapper;

import com.emendes.todoapi.dto.request.RegisterUserRequest;
import com.emendes.todoapi.dto.response.UserResponse;
import com.emendes.todoapi.mapper.impl.UserMapperImpl;
import com.emendes.todoapi.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;

/**
 * Unit tests para o record dto {@link UserMapperImpl}
 */
@DisplayName("Unit tests for UserMapperImpl")
class UserMapperImplTest {

  private final UserMapperImpl userMapper = new UserMapperImpl();

  @Test
  @DisplayName("toUser must return User when map successfully")
  void toUser_MustReturnUser_WhenMapSuccessfully() {
    RegisterUserRequest userRequest = RegisterUserRequest.builder()
        .name("Lorem Ipsum")
        .email("lorem@email.com")
        .password("1234567890")
        .confirmPassword("1234567890")
        .build();

    User actualUser = userMapper.toUser(userRequest);

    Assertions.assertThat(actualUser).isNotNull();
    Assertions.assertThat(actualUser.getName()).isNotNull().isEqualTo("Lorem Ipsum");
    Assertions.assertThat(actualUser.getEmail()).isNotNull().isEqualTo("lorem@email.com");
    Assertions.assertThat(actualUser.getPassword()).isNotNull().isEqualTo("1234567890");
    Assertions.assertThat(actualUser.getId()).isNull();
    Assertions.assertThat(actualUser.getCreationDate()).isNull();
    Assertions.assertThat(actualUser.getAuthorities()).isNotNull().isEmpty();
  }

  @Test
  @DisplayName("toUser must throw IllegalArgumentException when userRequest param is null")
  void toUser_mustThrowIllegalArgumentException_WhenUserRequestParamIsNull() {
    Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> userMapper.toUser(null))
        .withMessageContaining("UserRequest must not be null");
  }

  @Test
  @DisplayName("toUserResponse must return User when map successfully")
  void toUserResponse_MustReturnUser_WhenMapSuccessfully() {
    User user = User.builder()
        .id("asdmpimuoibvuysdubvausbdinv")
        .name("Lorem Ipsum")
        .email("lorem@email.com")
        .password("1234567890")
        .uriImage(URI.create("/api/images/1234567890"))
        .creationDate(LocalDateTime.parse("2023-07-05T10:30:00").truncatedTo(ChronoUnit.SECONDS))
        .authorities(Set.of("ROLE_USER"))
        .build();

    UserResponse actualUserResponse = userMapper.toUserResponse(user);

    Assertions.assertThat(actualUserResponse).isNotNull();
    Assertions.assertThat(actualUserResponse.id()).isNotNull().isEqualTo("asdmpimuoibvuysdubvausbdinv");
    Assertions.assertThat(actualUserResponse.name()).isNotNull().isEqualTo("Lorem Ipsum");
    Assertions.assertThat(actualUserResponse.email()).isNotNull().isEqualTo("lorem@email.com");
    Assertions.assertThat(actualUserResponse.uriImage()).isNotNull()
        .isEqualByComparingTo(URI.create("/api/images/1234567890"));
    Assertions.assertThat(actualUserResponse.creationDate()).isNotNull().isEqualTo("2023-07-05T10:30:00");
  }

  @Test
  @DisplayName("toUserResponse must throw IllegalArgumentException when userRequest param is null")
  void toUserResponse_mustThrowIllegalArgumentException_WhenUserRequestParamIsNull() {
    Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> userMapper.toUserResponse(null))
        .withMessageContaining("User must not be null");
  }

}