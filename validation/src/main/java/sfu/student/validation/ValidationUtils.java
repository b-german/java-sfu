package sfu.student.validation;

import java.util.List;
import java.util.Objects;

public class ValidationUtils {

  public static <T> ValidationResult validate(T validated, List<Validator<T>> validators) {
    ValidationResult result = new ValidationResult();
    for (Validator<T> validator : validators) {
      validator.apply(validated, result);
    }

    return result;
  }

  public static Validator<String> notEmptyString(String field) {
    return (string, result) -> {
      if (Objects.nonNull(string) && !string.isEmpty()) {
        return result;
      }
      return result.addError("Значение поля '%s' не может быть пустым".formatted(field));
    };
  }

  public static Validator<String> lengthIn(String field, int min, int max) {
    return (str, result) -> Objects.nonNull(str) && str.length() >= min && str.length() <= max
        ? result
        : result.addError(
            "Значение поля '%s' должно быть от %s до %s символов".formatted(field, min, max));
  }

  public static Validator<Integer> inRange(String field, Integer min, Integer max) {
    return (num, result) -> Objects.nonNull(num) && num >= min && num <= max
        ? result
        : result.addError(
            "Значение поля '%s' должно быть не меньше %s и не больше %s".formatted(field, min,
                max));
  }

  public static Validator<Integer> positive(String field) {
    return inRange(field, 0, Integer.MAX_VALUE);
  }


}
