package com.emendes.todoapi.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class que representa o Document user no banco de dados.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document
public class User implements UserDetails {

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
    initAuthorities();

    authorities.add(role);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    initAuthorities();

    return this.authorities.stream().map(SimpleGrantedAuthority::new)
        .collect(Collectors.toSet());
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  /**
   * Inicializa authorities caso seja null.
   */
  private void initAuthorities() {
    if (this.authorities == null) {
      this.authorities = new HashSet<>();
    }
  }
}
