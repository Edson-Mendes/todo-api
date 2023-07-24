package com.emendes.todoapi.controller;

import com.emendes.todoapi.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller o qual é mapeado as requisições de /api/images.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/images")
public class ImageController {

  private final ImageService imageService;

  /**
   * Trata requisição GET /api/images.
   *
   * @param imageId identificador da imagem solicitada.
   */
  @GetMapping("/{id}")
  public ResponseEntity<Resource> findById(@PathVariable(name = "id") String imageId) {
    Resource resource = imageService.findById(imageId);
    return ResponseEntity.ok()
        .contentType(MediaType.IMAGE_JPEG)
        .body(resource);
  }

}
