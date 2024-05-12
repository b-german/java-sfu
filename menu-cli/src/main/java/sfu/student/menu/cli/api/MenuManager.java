package sfu.student.menu.cli.api;

import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Supplier;

public class MenuManager<T extends Enum<?>> {

  public static final String PROMPT_TEXT = "Выберите пункт меню: ";
  public static final String ERROR_TEXT = "Не найден введенный пункт меню!";
  private final MenuOption<T>[] options;
  private final UserInputManager inputManager;

  public MenuManager(MenuOption<T>[] options) {
    this.options = options;
    this.inputManager = null;
  }

  public MenuManager(MenuOption<T>[] options, UserInputManager userInputManager) {
    this.options = options;
    this.inputManager = userInputManager;
  }

  public static MenuManager<BooleanMenuValue> getBooleanMenu(UserInputManager userInputManager) {
    return new MenuManager<>(BooleanMenuValue.values(), userInputManager);
  }

  public T getChoice(String promptText, String errorText, Supplier<String> supplier) {
    MenuOption<T> result = null;

    while (Objects.isNull(result)) {
      print(promptText);
      Optional<MenuOption<T>> choice = findByCode(supplier.get());
      if (choice.isPresent()) {
        result = choice.get();
      } else {
        System.out.println(errorText);
      }
    }

    return result.getEnumValue();
  }

  public T getChoice(String prompt) {
    return getChoice(prompt, ERROR_TEXT, inputManager::getStringSilent);
  }

  public T getChoice() {
    return getChoice(PROMPT_TEXT);
  }

  public T getChoice(Scanner scanner) {
    return getChoice(PROMPT_TEXT, ERROR_TEXT, scanner::nextLine);
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

  private void print(String promptText) {
    System.out.printf("%n%s%n", promptText);
    for (MenuOption<T> option : options) {
      System.out.println(option.asMenuOption());
    }
  }
}
