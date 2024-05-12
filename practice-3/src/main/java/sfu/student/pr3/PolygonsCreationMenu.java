package sfu.student.pr3;

import sfu.student.menu.cli.api.MenuOption;

public enum PolygonsCreationMenu implements MenuOption<PolygonsCreationMenu> {
  POLYGON("1", "Многоугольник"),
  RECTANGLE("2", "Прямоугольник"),
  SQUARE("3", "Квадрат"),
  TRIANGLE("4", "Треугольник");

  private final String code;
  private final String text;

  PolygonsCreationMenu(String code, String text) {
    this.code = code;
    this.text = text;
  }

  @Override
  public String getCode() {
    return code;
  }

  @Override
  public String getText() {
    return text;
  }
}
