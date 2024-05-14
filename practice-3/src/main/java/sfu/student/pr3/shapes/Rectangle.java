package sfu.student.pr3.shapes;

import java.util.Objects;
import sfu.student.validation.ValidationResult;
import sfu.student.validation.ValidationUtils;

public class Rectangle extends Polygon {

  private final Integer length;
  private final Integer width;

  private final String comment;

  public Rectangle() {
    super(4, "ABCD");
    length = 1;
    width = 1;
    comment = "Комментарий к прямоугольнику";
  }

  public Rectangle(String nameByVertices, Integer length, Integer width, String comment) {
    super(4, nameByVertices);
    this.length = length;
    this.width = width;
    this.comment = comment;
  }

  @Override
  public ValidationResult getValidationResult() {
    ValidationResult result = super.getValidationResult();

    ValidationUtils.inRange("Длина", 1, Integer.MAX_VALUE).apply(length, result);
    ValidationUtils.inRange("Ширина", 1, Integer.MAX_VALUE).apply(width, result);
    ValidationUtils.notEmptyString("Комментарий").apply(comment, result);

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
    Rectangle rectangle = (Rectangle) object;
    return Objects.equals(length, rectangle.length) && Objects.equals(width,
        rectangle.width) && Objects.equals(comment, rectangle.comment);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), length, width, comment);
  }

  @Override
  public String toString() {
    return "Rectangle{" +
        "length=" + length +
        ", width=" + width +
        ", comment='" + comment + '\'' +
        "} " + super.toString();
  }
}
