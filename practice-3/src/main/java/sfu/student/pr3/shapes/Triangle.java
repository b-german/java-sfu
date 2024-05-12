package sfu.student.pr3.shapes;

import java.util.Objects;
import sfu.student.validation.ValidationResult;
import sfu.student.validation.ValidationUtils;

public class Triangle extends Polygon {

  private final Integer base;
  private final String color;

  public Triangle() {
    super(3, "ABC");
    base = 1;
    color = "Белый";
  }

  public Triangle(String nameByVertices, Integer base, String color) {
    super(3, nameByVertices);
    this.base = base;
    this.color = color;
  }

  @Override
  public ValidationResult getValidationResult() {
    ValidationResult result = super.getValidationResult();
    ValidationUtils.inRange("Основание", 1, Integer.MAX_VALUE).apply(base, result);
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
    Triangle triangle = (Triangle) object;
    return Objects.equals(base, triangle.base) && Objects.equals(color,
        triangle.color);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), base, color);
  }

  @Override
  public String toString() {
    return "Triangle{" +
        "base=" + base +
        ", color='" + color + '\'' +
        "} " + super.toString();
  }
}
