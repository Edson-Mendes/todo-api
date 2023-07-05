package com.emendes.todoapi.repository;

import com.emendes.todoapi.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository interface to access {@link User}s.
 */
public interface UserRepository extends MongoRepository<User, String> {

  boolean existsByEmail(String email);

}
