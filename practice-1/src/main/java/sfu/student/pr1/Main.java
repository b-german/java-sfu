package sfu.student.pr1;

import java.util.Scanner;
import sfu.student.menu.cli.api.MenuManager;

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
      MenuManager<MenuChoice> menuManager = new MenuManager<>(MenuChoice.values());
      MenuChoice userChoice = menuManager.getChoice(inputScanner);
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
}