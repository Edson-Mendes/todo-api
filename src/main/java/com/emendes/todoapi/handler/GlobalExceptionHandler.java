package com.emendes.todoapi.handler;

import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.stream.Collectors;

/**
 * Controller advice responsável por lidar com as exceptions que ocorrem a partir da camada controller.
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private static final String URI_TEMPLATE = "https://github.com/Edson-Mendes/todo-api/problem-detail/%s";

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatusCode status,
      @NonNull WebRequest request) {
    String fields = ex.getFieldErrors().stream().map(FieldError::getField)
        .collect(Collectors.joining("; "));
    String messages = ex.getFieldErrors().stream().map(FieldError::getDefaultMessage)
        .collect(Collectors.joining("; "));

    ProblemDetail problemDetail = ProblemDetail
        .forStatusAndDetail(HttpStatusCode.valueOf(400), "Some fields are invalid");

    problemDetail.setType(URI.create(String.format(URI_TEMPLATE, "/invalid-fields")));
    problemDetail.setTitle("Invalid fields");
    problemDetail.setProperty("fields", fields);
    problemDetail.setProperty("messages", messages);

    return ResponseEntity.badRequest().body(problemDetail);
  }

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ProblemDetail> handleResponseStatus(ResponseStatusException ex) {
    ProblemDetail problemDetail = ProblemDetail
        .forStatusAndDetail(ex.getStatusCode(), ex.getReason());

    problemDetail.setType(URI.create(String.format(URI_TEMPLATE, "/something-went-wrong")));
    problemDetail.setTitle(HttpStatus.valueOf(ex.getStatusCode().value()).name());

    return ResponseEntity.status(ex.getStatusCode()).body(problemDetail);
  }

}
