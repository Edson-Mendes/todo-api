package com.emendes.todoapi.util.component;

import com.emendes.todoapi.model.User;

/**
 * Interface component com as abstrações para manipular o usuário atual.
 */
public interface AuthenticationFacade {

  /**
   * Retorna o usuário logado atualmente.
   */
  User getCurrentUser();

}
