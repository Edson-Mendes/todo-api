package com.emendes.todoapi.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document
public class User {

  @Id
  private String id;
  private String name;
  private String email;
  private String password;
  private LocalDateTime creationDate;
  private Set<String> authorities = new HashSet<>();


  @Override
  public String toString() {
    return "User{" +
        "id='" + id + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(id, user.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  public void addAuthority(String role) {
    if (role == null || !role.startsWith("ROLE_")) {
      throw new IllegalArgumentException("Invalid role: " + role);
    }
    if (authorities == null) {
      authorities = new HashSet<>();
    }

    authorities.add(role);
  }
}
