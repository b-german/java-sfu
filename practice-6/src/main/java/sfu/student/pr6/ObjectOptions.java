package sfu.student.pr6;

import sfu.student.menu.cli.api.MenuOption;

public enum ObjectOptions implements MenuOption<ObjectOptions> {
  SCALAR_INTEGER("1", "Целое число"),
  OBJECT_STRING("2", "Строка");

  private final String code;
  private final String text;

  ObjectOptions(String code, String text) {
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
