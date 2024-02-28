package sfu.student.pr1;

import java.util.Optional;

public enum MenuChoice {
  INPUT("1", "Ввод данных"),
  EXECUTE("2", "Выполнение алгоритма"),
  DISPLAY("3", "Вывод результата"),
  EXIT("4", "Выход");

  private final String code;
  private final String text;

  MenuChoice(String code, String text) {
    this.code = code;
    this.text = text;
  }

  public static Optional<MenuChoice> findByCode(String code) {
    MenuChoice found = null;
    for (int i = 0; i < values().length; i++) {
      final var menuChoice = values()[i];
      if (menuChoice.getCode().equals(code)) {
        found = menuChoice;
      }
    }
    return Optional.ofNullable(found);
  }

  public static void print() {
    System.out.printf("%nВыберите пункт меню: %n");
    for (MenuChoice choice : values()) {
      System.out.println(choice.toString());
    }
  }

  public String getCode() {
    return code;
  }

  public String getText() {
    return text;
  }

  @Override
  public String toString() {
    return "%s) %s".formatted(getCode(), getText());
  }
}
