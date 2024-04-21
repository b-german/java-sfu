package sfu.student.pr1;

import sfu.student.menu.cli.api.MenuOption;

public enum MenuChoice implements MenuOption<MenuChoice> {
  INPUT("1", "Ввод данных вручную"),
  RANDOM_INPUT("2", "Генерация данных случайным образом"),
  EXECUTE("3", "Выполнение алгоритма"),
  DISPLAY("4", "Вывод результата"),
  EXIT("5", "Выход");

  private final String code;
  private final String text;

  MenuChoice(String code, String text) {
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
