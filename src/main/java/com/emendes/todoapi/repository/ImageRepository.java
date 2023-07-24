package com.emendes.todoapi.repository;

import com.emendes.todoapi.model.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository interface to access {@link Image}s.
 */
public interface ImageRepository extends MongoRepository<Image, String> {
}
