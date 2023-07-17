package com.emendes.todoapi.util.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Combina as anotações {@link Size} com valor min = max = 24 caracteres
 * e {@link Pattern} com o regex '[a-fA-F0-9]*' para validar um identificador de
 * documento salvo no banco de dados.
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Size(min = 24, max = 24, message = "id must be 24 characters long")
@Pattern(regexp = "[a-fA-F0-9]*", message = "id must be hexadecimal value")
public @interface IdValidation {

  String message() default "invalid id";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
