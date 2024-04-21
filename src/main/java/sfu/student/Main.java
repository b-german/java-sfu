package sfu.student;

import java.util.Scanner;

public class Main {

  public static final String AWAITING_INPUT = """
            
      Ожидается ввод... Для закрытия программы наберите 'Закрыть'.""";
  public static final String EXIT_COMMAND = "Закрыть";
  public static final String[] EMPTY_ARGS = {""};
  public static final String MENU = """
      Введите номер работы, чтобы её запустить.
      1) Практическая работа 1
      2) Практическая работа 2""";
  public static final String NO_MENU_OPTION_TEMPLATE = "Пункта меню '%s' нет%n";

  public static void main(String[] args) {
    String input;
    Scanner inputScanner = new Scanner(System.in);
    do {
      System.out.println(MENU);

      System.out.println(AWAITING_INPUT);
      input = inputScanner.nextLine();

      switch (input) {
        case "1" -> sfu.student.pr1.Main.main(EMPTY_ARGS);
        case "2" -> sfu.student.pr2.Main.main(EMPTY_ARGS);
        case EXIT_COMMAND -> noop();
        default -> System.out.printf(NO_MENU_OPTION_TEMPLATE, input);
      }
    } while (!EXIT_COMMAND.equalsIgnoreCase(input));
    inputScanner.close();
  }

  /**
   * No operation method that's used when action is required, but the action shouldn't do anything
   */
  public static void noop() {
    //no operation
  }

}