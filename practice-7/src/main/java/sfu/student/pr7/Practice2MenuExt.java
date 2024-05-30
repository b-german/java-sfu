package sfu.student.pr7;

import sfu.student.menu.cli.api.MenuOption;

public enum Practice2MenuExt implements MenuOption<Practice2MenuExt> {
  ADD_TREE("1", "Добавить дерево"),
  ADD_DETAILED_TREE("2", "Добавить дерево с параметрами"),
  EDIT("3", "Изменить один параметр"),
  DISPLAY("4", "Показать деревья"),
  SORT("5", "Отсортировать деревья по полю"),
  EXIT("6", "Выход"),
  STREAM_API_OPTIONS("7", "Перейти в меню работы с потоками");

  private final String code;
  private final String text;

  Practice2MenuExt(String code, String text) {
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
