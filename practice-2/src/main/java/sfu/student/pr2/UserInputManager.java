package sfu.student.pr2;

import java.util.Scanner;

public class UserInputManager {

  private final Scanner inputScanner;

  public UserInputManager(Scanner inputScanner) {
    this.inputScanner = inputScanner;
  }

  public String getString() {
    System.out.print("Введите значение: ");
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
    while (true) {
      System.out.print("Введите целое число: ");
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
