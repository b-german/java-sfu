package sfu.student.pr5;

import sfu.student.menu.cli.api.MenuOption;

public enum Practice2Menu implements MenuOption<Practice2Menu> {
  ADD_TREE("1", "Добавить дерево"),
  ADD_DETAILED_TREE("2", "Добавить дерево с параметрами"),
  EDIT("3", "Изменить один параметр"),
  DISPLAY("4", "Показать деревья"),
  SORT("5", "Отсортировать деревья по полю"),
  EXIT("6", "Выход");

  private final String code;
  private final String text;

  Practice2Menu(String code, String text) {
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
