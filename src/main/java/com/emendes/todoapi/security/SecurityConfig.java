package com.emendes.todoapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

  public static final String ROLE_USER = "ROLE_USER";
  private final AuthenticationProvider authenticationProvider;
  private static final String[] POST_WHITELISTING = {"/api/users"};
  private static final String[] GET_WHITELISTING = {"/api/images/*"};

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable);

    http.httpBasic(Customizer.withDefaults()).authenticationProvider(authenticationProvider);

    http.authorizeHttpRequests(
        authorize -> {
          authorize.requestMatchers(HttpMethod.POST, POST_WHITELISTING).permitAll();
          authorize.requestMatchers(HttpMethod.GET, GET_WHITELISTING).permitAll();
          authorize.anyRequest().authenticated();
        }
    );

    return http.build();
  }

}
