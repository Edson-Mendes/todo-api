package com.emendes.todoapi.mapper.impl;

import com.emendes.todoapi.dto.request.RegisterUserRequest;
import com.emendes.todoapi.dto.response.UserResponse;
import com.emendes.todoapi.mapper.UserMapper;
import com.emendes.todoapi.model.User;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class UserMapperImpl implements UserMapper {

  @Override
  public User toUser(RegisterUserRequest userRequest) {
    Assert.notNull(userRequest, "UserRequest must not be null");

    return User.builder()
        .name(userRequest.name())
        .email(userRequest.email())
        .password(userRequest.password())
        .build();
  }

  @Override
  public UserResponse toUserResponse(User user) {
    Assert.notNull(user, "User must not be null");

    return UserResponse.builder()
        .id(user.getId())
        .name(user.getName())
        .email(user.getEmail())
        .uriImage(user.getUriImage())
        .creationDate(user.getCreationDate())
        .build();
  }

}
