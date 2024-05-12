package sfu.student.validation;

import java.util.StringJoiner;

public class ValidationResult {

  private static final String msgTemplate = "%s) %s";
  private final StringJoiner result;
  private int idx;

  public ValidationResult() {
    this.result = new StringJoiner(System.lineSeparator());
    this.idx = 1;
  }

  public boolean isValid() {
    return result.length() == 0;
  }

  public String get() {
    return result.toString();
  }

  public ValidationResult addError(String message) {
    result.add(String.format(msgTemplate, idx++, message));
    return this;
  }
}
