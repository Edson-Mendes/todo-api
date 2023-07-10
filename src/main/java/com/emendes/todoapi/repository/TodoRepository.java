package com.emendes.todoapi.repository;

import com.emendes.todoapi.model.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository interface to access Todos.
 */
public interface TodoRepository extends MongoRepository<Todo, String> {
}
