package sfu.student.menu.cli.api;

import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

public class MenuManager<T extends Enum<?>> {

  private final MenuOption<T>[] options;

  public MenuManager(MenuOption<T>[] options) {
    this.options = options;
  }

  public T getChoice(Scanner scanner) {
    MenuOption<T> result = null;

    while (Objects.isNull(result)) {
      print();
      Optional<MenuOption<T>> choice = findByCode(scanner.nextLine());
      if (choice.isPresent()) {
        result = choice.get();
      } else {
        System.out.println("Не найден введенный пункт меню!");
      }
    }

    return result.getEnumValue();
  }

  private Optional<MenuOption<T>> findByCode(String code) {
    MenuOption<T> found = null;
    for (final MenuOption<T> menuChoice : options) {
      if (menuChoice.getCode().equals(code)) {
        found = menuChoice;
      }
    }
    return Optional.ofNullable(found);
  }

  private void print() {
    System.out.printf("%nВыберите пункт меню: %n");
    for (MenuOption<T> option : options) {
      System.out.println(option.asMenuOption());
    }
  }
}
