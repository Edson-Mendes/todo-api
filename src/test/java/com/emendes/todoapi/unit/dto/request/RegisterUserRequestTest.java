package com.emendes.todoapi.unit.dto.request;

import com.emendes.todoapi.dto.request.RegisterUserRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Set;

/**
 * Unit tests para o record dto {@link RegisterUserRequest}
 */
@DisplayName("Unit tests for RegisterUserRequest")
class RegisterUserRequestTest {

  private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  /**
   * Classe com os testes de validação do campo RegisterUserRequest#name.
   */
  @Nested
  @DisplayName("Tests for Name validation")
  class NameValidation {

    private static final String NAME_PROPERTY = "name";

    @ParameterizedTest
    @ValueSource(strings = {
        "Xu",
        "Lorem Ipsum",
        "nameWith100CharacterNameWith100CharacterNameWith100CharacterNameWith100CharacterNameWith100Character"})
    @DisplayName("nameValidation must not return violations when name is valid")
    void nameValidation_MustNotReturnViolations_WhenNameIsValid(String validName) {
      RegisterUserRequest request = RegisterUserRequest.builder()
          .name(validName)
          .build();

      Set<ConstraintViolation<RegisterUserRequest>> actualViolations =
          validator.validateProperty(request, NAME_PROPERTY);

      Assertions.assertThat(actualViolations).isEmpty();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t", "\n"})
    @DisplayName("nameValidation must return violations when name is blank")
    void nameValidation_MustReturnViolations_WhenNameIsBlank(String blankName) {
      RegisterUserRequest request = RegisterUserRequest.builder()
          .name(blankName)
          .build();

      Set<ConstraintViolation<RegisterUserRequest>> actualViolations =
          validator.validateProperty(request, NAME_PROPERTY);

      List<String> actualMessages = actualViolations.stream().map(ConstraintViolation::getMessage).toList();

      Assertions.assertThat(actualMessages).isNotEmpty()
          .contains("name must not be blank");
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "X",
        "nameWithMoreThan100CharactersNameWithMoreThan100CharactersNameWithMoreThan100CharactersNameWithMoreTha"})
    @DisplayName("nameValidation must return violations when name length is less than 2 or bigger than 100")
    void nameValidation_MustReturnViolations_WhenNameLengthIsLessThan2OrBiggerThan100(String invalidName) {
      RegisterUserRequest request = RegisterUserRequest.builder()
          .name(invalidName)
          .build();

      Set<ConstraintViolation<RegisterUserRequest>> actualViolations =
          validator.validateProperty(request, NAME_PROPERTY);

      List<String> actualMessages = actualViolations.stream().map(ConstraintViolation::getMessage).toList();

      Assertions.assertThat(actualMessages).isNotEmpty()
          .contains("name must contain between 2 and 100 characters long");
    }

  }

  /**
   * Classe com os testes de validação do campo RegisterUserRequest#email
   */
  @Nested
  @DisplayName("Tests for Email validation")
  class EmailValidation {

    private static final String EMAIL_PROPERTY = "email";

    @ParameterizedTest
    @ValueSource(strings = {
        "lorem@email.com",
        "loremipsumdolorsitametloremipsumdolorsitamet@email.com"})
    @DisplayName("emailValidation must not return violations when email is valid")
    void emailValidation_MustNotReturnViolations_WhenEmailIsValid(String validEmail) {
      RegisterUserRequest request = RegisterUserRequest.builder()
          .email(validEmail)
          .build();

      Set<ConstraintViolation<RegisterUserRequest>> actualViolations =
          validator.validateProperty(request, EMAIL_PROPERTY);

      Assertions.assertThat(actualViolations).isEmpty();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t", "\n"})
    @DisplayName("emailValidation must return violations when email is blank")
    void emailValidation_MustReturnViolations_WhenEmailIsBlank(String blankEmail) {
      RegisterUserRequest request = RegisterUserRequest.builder()
          .email(blankEmail)
          .build();

      Set<ConstraintViolation<RegisterUserRequest>> actualViolations =
          validator.validateProperty(request, EMAIL_PROPERTY);

      List<String> actualMessages = actualViolations.stream().map(ConstraintViolation::getMessage).toList();

      Assertions.assertThat(actualMessages).isNotEmpty()
          .contains("email must not be blank");
    }

    @Test
    @DisplayName("emailValidation must return violations when email length is bigger than 150")
    void emailValidation_MustReturnViolations_WhenEmailLengthIsBiggerThan150() {
      String emailWithMoreThan150Characters = "loremipsumdolorsitametloremipsumdolorsitamet" +
          "loremipsumdolorsitametloremipsumdolorsitamet" +
          "loremipsumdolorsitametloremipsumdolorsitamet" +
          "loremipsumdolorsitametloremipsumdolorsitamet@email.com";

      Assertions.assertThat(emailWithMoreThan150Characters).hasSizeGreaterThan(150);

      RegisterUserRequest request = RegisterUserRequest.builder()
          .email(emailWithMoreThan150Characters)
          .build();

      Set<ConstraintViolation<RegisterUserRequest>> actualViolations =
          validator.validateProperty(request, EMAIL_PROPERTY);

      List<String> actualMessages = actualViolations.stream().map(ConstraintViolation::getMessage).toList();

      Assertions.assertThat(actualMessages).isNotEmpty()
          .contains("email must contain max 150 characters long");
    }

    @ParameterizedTest
    @ValueSource(strings = {"invalidemail", "@email.com", "invalidemailcom"})
    @DisplayName("emailValidation must return violations when email is not well formed")
    void emailValidation_MustReturnViolations_WhenEmailIsNotWellFormed(String invalidEmail) {
      RegisterUserRequest request = RegisterUserRequest.builder()
          .email(invalidEmail)
          .build();

      Set<ConstraintViolation<RegisterUserRequest>> actualViolations =
          validator.validateProperty(request, EMAIL_PROPERTY);

      List<String> actualMessages = actualViolations.stream().map(ConstraintViolation::getMessage).toList();

      Assertions.assertThat(actualMessages).isNotEmpty()
          .contains("must be a well formed email");
    }
  }

  /**
   * Classe com os testes de validação do campo RegisterUserRequest#password.
   */
  @Nested
  @DisplayName("Tests for Password validation")
  class PasswordValidation {

    private static final String PASSWORD_PROPERTY = "password";

    @ParameterizedTest
    @ValueSource(strings = {
        "12345678",
        "123456789012345678901234567890",
        "1234567890abcdef"})
    @DisplayName("passwordValidation must not return violations when password is valid")
    void passwordValidation_MustNotReturnViolations_WhenPasswordIsValid(String validPassword) {
      RegisterUserRequest request = RegisterUserRequest.builder()
          .password(validPassword)
          .build();

      Set<ConstraintViolation<RegisterUserRequest>> actualViolations =
          validator.validateProperty(request, PASSWORD_PROPERTY);

      Assertions.assertThat(actualViolations).isEmpty();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t", "\n"})
    @DisplayName("passwordValidation must return violations when password is blank")
    void passwordValidation_MustReturnViolations_WhenPasswordIsBlank(String blankPassword) {
      RegisterUserRequest request = RegisterUserRequest.builder()
          .password(blankPassword)
          .build();

      Set<ConstraintViolation<RegisterUserRequest>> actualViolations =
          validator.validateProperty(request, PASSWORD_PROPERTY);

      List<String> actualMessages = actualViolations.stream().map(ConstraintViolation::getMessage).toList();

      Assertions.assertThat(actualMessages).isNotEmpty()
          .contains("password must not be blank");
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "1234567",
        "1234567890123456789012345678900"})
    @DisplayName("passwordValidation must return violations when password length is less than 2 or bigger than 100")
    void passwordValidation_MustReturnViolations_WhenPasswordLengthIsLessThan8OrBiggerThan30(String invalidPassword) {
      RegisterUserRequest request = RegisterUserRequest.builder()
          .password(invalidPassword)
          .build();

      Set<ConstraintViolation<RegisterUserRequest>> actualViolations =
          validator.validateProperty(request, PASSWORD_PROPERTY);

      List<String> actualMessages = actualViolations.stream().map(ConstraintViolation::getMessage).toList();

      Assertions.assertThat(actualMessages).isNotEmpty()
          .contains("password must contain between 8 and 30 characters long");
    }

  }

  /**
   * Classe com os testes de validação do campo RegisterUserRequest#confirmPassword.
   */
  @Nested
  @DisplayName("Tests for ConfirmPassword validation")
  class ConfirmPasswordValidation {

    private static final String CONFIRM_PASSWORD_PROPERTY = "confirmPassword";

    @ParameterizedTest
    @ValueSource(strings = {
        "12345678",
        "123456789012345678901234567890",
        "1234567890abcdef"})
    @DisplayName("confirmPasswordValidation must not return violations when confirmPassword is valid")
    void confirmPasswordValidation_MustNotReturnViolations_WhenConfirmPasswordIsValid(String validConfirmPassword) {
      RegisterUserRequest request = RegisterUserRequest.builder()
          .confirmPassword(validConfirmPassword)
          .build();

      Set<ConstraintViolation<RegisterUserRequest>> actualViolations =
          validator.validateProperty(request, CONFIRM_PASSWORD_PROPERTY);

      Assertions.assertThat(actualViolations).isEmpty();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t", "\n"})
    @DisplayName("confirmPasswordValidation must return violations when confirmPassword is blank")
    void confirmPasswordValidation_MustReturnViolations_WhenConfirmPasswordIsBlank(String blankConfirmPassword) {
      RegisterUserRequest request = RegisterUserRequest.builder()
          .confirmPassword(blankConfirmPassword)
          .build();

      Set<ConstraintViolation<RegisterUserRequest>> actualViolations =
          validator.validateProperty(request, CONFIRM_PASSWORD_PROPERTY);

      List<String> actualMessages = actualViolations.stream().map(ConstraintViolation::getMessage).toList();

      Assertions.assertThat(actualMessages).isNotEmpty()
          .contains("confirm_password must not be blank");
    }

  }

}