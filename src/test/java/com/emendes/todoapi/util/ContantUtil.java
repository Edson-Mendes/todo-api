package com.emendes.todoapi.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * Class para manter contantes usadas em testes.
 */
public class ContantUtil {

  private ContantUtil(){}

  public static final Pageable PAGEABLE = PageRequest.of(0, 10);

}
