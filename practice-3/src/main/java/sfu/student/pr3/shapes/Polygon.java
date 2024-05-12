package sfu.student.pr3.shapes;

import java.util.Objects;
import sfu.student.validation.ValidationResult;
import sfu.student.validation.ValidationUtils;

public class Polygon {

  private final Integer verticesCount;

  /**
   * For e.g. square ABCD, or a triangle ACD
   */
  private final String nameByVertices;

  public Polygon() {
    verticesCount = 1;
    nameByVertices = "A";
  }

  public Polygon(Integer verticesCount, String nameByVertices) {
    this.verticesCount = verticesCount;
    this.nameByVertices = nameByVertices;
  }

  public ValidationResult getValidationResult() {
    ValidationResult result = new ValidationResult();
    ValidationUtils.lengthIn("Название фигуры", verticesCount, verticesCount)
        .apply(nameByVertices, result);
    ValidationUtils.positive("Количество вершин").apply(verticesCount, result);
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
    Polygon polygon = (Polygon) object;
    return Objects.equals(verticesCount, polygon.verticesCount) && Objects.equals(
        nameByVertices, polygon.nameByVertices);
  }

  @Override
  public int hashCode() {
    return Objects.hash(verticesCount, nameByVertices);
  }

  @Override
  public String toString() {
    return "Polygon{" +
        "verticesCount=" + verticesCount +
        ", nameByVertices='" + nameByVertices + '\'' +
        '}';
  }
}
