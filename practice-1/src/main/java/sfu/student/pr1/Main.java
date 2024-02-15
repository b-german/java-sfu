package sfu.student.pr1;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Main {

  public static final String HEADER = """
      Задание: Пр 1
      Входные данные: текст.
      Результат работы алгоритма: отсортированный по возрастанию длины слова массив слов.""";
  public static final String AWAITING_INPUT = """
            
      Ожидается ввод... Для закрытия программы наберите 'Выход'.""";
  public static final String EXIT_COMMAND = "Выход";

  public static final String REGEXP_NOT_CHARS = "[^A-zА-я]+";

  public static void main(String[] args) {
    Scanner inputScanner = new Scanner(System.in);

    System.out.println(HEADER);
    String input;
    do {
      System.out.println(AWAITING_INPUT);
      input = inputScanner.nextLine();

      if (!EXIT_COMMAND.equalsIgnoreCase(input)) {
        String[] stringArray = input.split(REGEXP_NOT_CHARS);
        Arrays.sort(stringArray, Comparator.comparing(String::length));
        System.out.println(Arrays.toString(stringArray));
      }

    } while (!EXIT_COMMAND.equalsIgnoreCase(input));
  }

}