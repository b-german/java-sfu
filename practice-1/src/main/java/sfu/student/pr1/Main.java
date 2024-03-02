package sfu.student.pr1;

import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

public class Main {

  public static final String HEADER = """
      Задание: Пр 1
      Входные данные: текст.
      Результат работы алгоритма: отсортированный по возрастанию длины слова массив слов.""";

  public static void main(String[] args) {
    Scanner inputScanner = new Scanner(System.in);
    InputStringByLengthSorter sorter = new InputStringByLengthSorter();
    System.out.println(HEADER);

    boolean exitFlag = true;
    while (exitFlag) {
      MenuChoice userChoice = getValidMenuChoice(inputScanner);

      switch (userChoice) {
        case INPUT -> {
          System.out.println("Ожидается ввод: ");
          sorter.acceptInputString(inputScanner.nextLine());
        }
        case RANDOM_INPUT -> sorter.acceptInputString(RandomTextGenerator.generateRandomText());
        case EXECUTE -> sorter.sortInputString();
        case DISPLAY -> sorter.printResult();
        case EXIT -> exitFlag = false;
      }
    }
  }

  private static MenuChoice getValidMenuChoice(Scanner scanner) {
    MenuChoice result = null;

    while (Objects.isNull(result)) {
      MenuChoice.print();
      Optional<MenuChoice> choice = MenuChoice.findByCode(scanner.nextLine());
      if (choice.isPresent()) {
        result = choice.get();
      } else {
        System.out.println("Не найден введенный пункт меню!");
      }
    }

    return result;
  }
}