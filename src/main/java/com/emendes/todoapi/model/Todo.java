package com.emendes.todoapi.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;

/**
 * Class que representa o Document todo no banco de dados
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document
public class Todo {

  @Id
  private String id;
  private String description;
  private boolean concluded;
  private LocalDateTime creationDate;
  @DocumentReference(lazy = true)
  private User user;

}
