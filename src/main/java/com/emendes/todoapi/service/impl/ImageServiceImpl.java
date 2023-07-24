package com.emendes.todoapi.service.impl;

import com.emendes.todoapi.model.Image;
import com.emendes.todoapi.repository.ImageRepository;
import com.emendes.todoapi.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.http.HttpStatusCode;
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
    // TODO: Validar o parâmentro file.
    try {
      Binary binaryFile = new Binary(BsonBinarySubType.BINARY, file.getBytes());

      Image image = Image.builder()
          .size(file.getSize())
          .file(binaryFile)
          .build();

      imageRepository.save(image);

      return URI.create(String.format(URI_IMAGE_TEMPLATE, image.getId()));
    } catch (IOException ioException) {
      throw new ResponseStatusException(HttpStatusCode.valueOf(500), "File access error");
    }

  }

}
