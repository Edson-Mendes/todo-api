package com.emendes.todoapi.util.faker;

import java.net.URI;

/**
 * Classe auxiliar com fake document Image e fake DTOs relacionados a Image para uso em testes.
 */
public class ImageFaker {

  private ImageFaker() {}

  public static URI uri() {
    return URI.create("/api/images/1234567890");
  }

}
