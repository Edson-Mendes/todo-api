package com.emendes.todoapi.repository;

import com.emendes.todoapi.model.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Repository interface to access Todos.
 */
public interface TodoRepository extends MongoRepository<Todo, String> {

  /**
   * Busca paginada de Todo por user$id.
   *
   * @param userId   identificador do User.
   * @param pageable objeto que contém os parâmetros da paginação.
   * @return Page of Todos
   */
  Page<Todo> findByUserId(String userId, Pageable pageable);

  /**
   * Busca todo por todoId e userId
   *
   * @param todoId identificador do Todo.
   * @param userId identificador do User.
   * @return Optional of Todo.
   */
  Optional<Todo> findByIdAndUserId(String todoId, String userId);

}
