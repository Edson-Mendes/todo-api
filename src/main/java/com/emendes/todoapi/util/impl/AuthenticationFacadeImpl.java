package com.emendes.todoapi.util.impl;

import com.emendes.todoapi.model.User;
import com.emendes.todoapi.util.AuthenticationFacade;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

/**
 * Implementação de {@link AuthenticationFacade}
 */
@Component
public class AuthenticationFacadeImpl implements AuthenticationFacade {

  @Override
  public User getCurrentUser() {
    return (User) getPrincipal();
  }

  /**
   * Devolve o usuário atual que está no SecurityContext.
   */
  private Object getPrincipal() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication.isAuthenticated()) {
      return authentication.getPrincipal();
    }

    throw new ResponseStatusException(HttpStatusCode.valueOf(400), "client non authenticated");
  }

}
