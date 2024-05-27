package sfu.student.pr6;

import sfu.student.menu.cli.api.MenuOption;

public enum ArrayStackOptions implements MenuOption<ArrayStackOptions> {
  IS_EMPTY("1", "Проверить, стек пуст/не пуст"),
  PUSH("2", "Втолкнуть элемент"),
  POP("3", "Вытолкнуть элемент"),
  PEEK("4", "Получить вершину стека"),
  SWAP("5", "Обменять значения двух верхних элементов стека"),
  REMOVE("6", "Извлечь из стека первое вхождение передаваемого значения"),
  FIND("7", "Найти индекс первого вхождения передаваемого значения"),
  PRINT("8", "Вывод стека на экран"),
  EXIT("9", "Выход из меню коллекции");

  private final String code;
  private final String text;

  ArrayStackOptions(String code, String text) {
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
