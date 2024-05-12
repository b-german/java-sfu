package sfu.student.pr3;

import sfu.student.menu.cli.api.MenuOption;

public enum PolygonsMainMenu implements MenuOption<PolygonsMainMenu> {
  ADD("1", "Добавить фигуру"),
  REMOVE("2", "Удалить фигуру"),
  DISPLAY("3", "Показать фигуры"),
  COMPARE("4", "Сравнить фигуры"),
  EXIT("5", "Выход");

  private final String code;
  private final String text;

  PolygonsMainMenu(String code, String text) {
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
