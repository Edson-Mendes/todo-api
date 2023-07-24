package com.emendes.todoapi.service;

import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

/**
 * Interface service com as abstrações para manipulação do recurso Image.
 */
public interface ImageService {

  /**
   * Salva um arquivo.
   *
   * @param file arquivo a ser salvo.
   * @return URI do arquivo salvo.
   */
  URI store(MultipartFile file);

}
