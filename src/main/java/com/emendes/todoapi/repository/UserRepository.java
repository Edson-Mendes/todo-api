package com.emendes.todoapi.repository;

import com.emendes.todoapi.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Repository interface to access {@link User}s.
 */
public interface UserRepository extends MongoRepository<User, String> {

  /**
   * Verifica se existe usuário cadastrado com dado email.
   *
   * @param email a ser verificado.
   * @return true se já existe um usuário com o email informado, false caso contrário.
   */
  boolean existsByEmail(String email);

  /**
   * Busca usuário por email (username).
   *
   * @param username a ser buscado.
   * @return Optional of User.
   */
  Optional<User> findByEmail(String username);

}
