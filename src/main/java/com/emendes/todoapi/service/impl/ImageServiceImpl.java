package com.emendes.todoapi.service.impl;

import com.emendes.todoapi.model.Image;
import com.emendes.todoapi.repository.ImageRepository;
import com.emendes.todoapi.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.util.InMemoryResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.URI;

/**
 * Implementação de {@link ImageService}.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {

  private final ImageRepository imageRepository;

  private static final String URI_IMAGE_TEMPLATE = "/api/images/%s";

  /**
   * @throws ResponseStatusException em caso de erro na leiturado arquivo.
   */
  @Override
  public URI store(MultipartFile file) {
    log.info("attempt to store image");
    // TODO: Validar o parâmentro file.
    try {
      Binary binaryFile = new Binary(BsonBinarySubType.BINARY, file.getBytes());

      Image image = Image.builder()
          .size(file.getSize())
          .file(binaryFile)
          .build();

      imageRepository.save(image);

      log.info("image with id: {} stored successful", image.getId());
      return URI.create(String.format(URI_IMAGE_TEMPLATE, image.getId()));
    } catch (IOException ioException) {
      log.info("File access error - Exception message: {}", ioException.getMessage());
      throw new ResponseStatusException(HttpStatusCode.valueOf(500), "File access error");
    }

  }

  @Override
  public Resource findById(String imageId) {
    log.info("attempt to fetch image with id: {}", imageId);

    Image image = imageRepository.findById(imageId)
        .orElseThrow(() -> {
          log.info("image not found for id: {}", imageId);
          return new ResponseStatusException(HttpStatusCode.valueOf(400), "Image not found");
        });

    return new InMemoryResource(image.getFile().getData());
  }

}
