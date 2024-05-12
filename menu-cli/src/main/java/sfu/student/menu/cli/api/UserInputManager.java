package sfu.student.menu.cli.api;

import java.util.Scanner;

public class UserInputManager {

  private final Scanner inputScanner;

  public UserInputManager(Scanner inputScanner) {
    this.inputScanner = inputScanner;
  }

  public String getString() {
    return getString("Введите значение: ");
  }

  public String getString(String prompt) {
    System.out.print(prompt);
    return inputScanner.nextLine();
  }

  public String getStringSilent() {
    return inputScanner.nextLine();
  }

  public Float getFloat() {
    while (true) {
      System.out.print("Введите число с запятой: ");
      String floatString = inputScanner.nextLine();

      float floatValue;
      try {
        floatValue = Float.parseFloat(floatString);
      } catch (NumberFormatException exception) {
        System.out.println("Некорректное число с запятой!");
        continue;
      }
      return floatValue;
    }
  }

  public Integer getInteger() {
    return getInteger("Введите целое число: ");
  }

  public Integer getInteger(String prompt) {
    while (true) {
      System.out.print(prompt);
      String intString = inputScanner.nextLine();

      int intValue;
      try {
        intValue = Integer.parseInt(intString);
      } catch (NumberFormatException exception) {
        System.out.println("Некорректное целое число!");
        continue;
      }
      return intValue;
    }
  }
}
