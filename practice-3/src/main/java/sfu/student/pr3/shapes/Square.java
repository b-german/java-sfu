package sfu.student.pr3.shapes;

import java.util.Objects;
import sfu.student.validation.ValidationResult;
import sfu.student.validation.ValidationUtils;

public class Square extends Rectangle {

  private final int sideLength;
  private final String color;

  public Square() {
    super("ABCD", 1, 1);
    sideLength = 1;
    color = "Белый";
  }

  public Square(String nameByVertices, Integer sideLength, String color) {
    super(nameByVertices, sideLength, sideLength);
    this.sideLength = sideLength;
    this.color = color;
  }

  @Override
  public ValidationResult getValidationResult() {
    ValidationResult result = super.getValidationResult();
    ValidationUtils.inRange("Сторона", 1, Integer.MAX_VALUE).apply(sideLength, result);
    ValidationUtils.notEmptyString("Цвет").apply(color, result);
    return result;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    if (!super.equals(object)) {
      return false;
    }
    Square square = (Square) object;
    return sideLength == square.sideLength && Objects.equals(color, square.color);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), sideLength, color);
  }

  @Override
  public String toString() {
    return "Square{" +
        "sideLength=" + sideLength +
        ", color='" + color + '\'' +
        "} " + super.toString();
  }
}
