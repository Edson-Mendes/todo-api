package com.emendes.todoapi.unit.dto.request;

import com.emendes.todoapi.dto.request.TodoRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Set;

/**
 * Unit tests para o record dto {@link TodoRequest}
 */
@DisplayName("Unit tests for CreateTodoRequest")
class TodoRequestTest {

  private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  /**
   * Classe com os testes de validação do campo CreateTodoRequest#description.
   */
  @Nested
  @DisplayName("Tests for Description validation")
  class DescriptionValidation {

    private static final String DESCRIPTION_PROPERTY = "description";

    @ParameterizedTest
    @ValueSource(strings = {
        "Xu",
        "Fazer tarefa X",
        "descriptionWith255CharactersDescriptionWith255CharactersDescriptionWith255Characters" +
            "DescriptionWith255CharactersDescriptionWith255CharactersDescriptionWith255Characters" +
            "DescriptionWith255CharactersDescriptionWith255CharactersDescriptionWith255CharactersDes"})
    @DisplayName("descriptionValidation must not return violations when description is valid")
    void descriptionValidation_MustNotReturnViolations_WhenDescriptionIsValid(String validDescription) {
      Assertions.assertThat(validDescription).hasSizeGreaterThan(1).hasSizeLessThan(256);

      TodoRequest request = TodoRequest.builder()
          .description(validDescription)
          .build();

      Set<ConstraintViolation<TodoRequest>> actualViolations =
          validator.validateProperty(request, DESCRIPTION_PROPERTY);

      Assertions.assertThat(actualViolations).isEmpty();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t", "\n"})
    @DisplayName("descriptionValidation must return violations when description is blank")
    void descriptionValidation_MustReturnViolations_WhenDescriptionIsBlank(String blankDescription) {
      TodoRequest request = TodoRequest.builder()
          .description(blankDescription)
          .build();

      Set<ConstraintViolation<TodoRequest>> actualViolations =
          validator.validateProperty(request, DESCRIPTION_PROPERTY);

      List<String> actualMessages = actualViolations.stream().map(ConstraintViolation::getMessage).toList();

      Assertions.assertThat(actualMessages).isNotEmpty()
          .contains("description must not be blank");
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "X",
        "descriptionWith255CharactersDescriptionWith255CharactersDescriptionWith255Characters" +
            "DescriptionWith255CharactersDescriptionWith255CharactersDescriptionWith255Characters" +
            "DescriptionWith255CharactersDescriptionWith255CharactersDescriptionWith255CharactersDesc"})
    @DisplayName("descriptionValidation must return violations when description length is less than 2 or bigger than 255")
    void descriptionValidation_MustReturnViolations_WhenDescriptionLengthIsLessThan2OrBiggerThan100(String invalidDescription) {
      TodoRequest request = TodoRequest.builder()
          .description(invalidDescription)
          .build();

      Set<ConstraintViolation<TodoRequest>> actualViolations =
          validator.validateProperty(request, DESCRIPTION_PROPERTY);

      List<String> actualMessages = actualViolations.stream().map(ConstraintViolation::getMessage).toList();

      Assertions.assertThat(actualMessages).isNotEmpty()
          .contains("description must contain between 2 and 255 characters long");
    }

  }

}