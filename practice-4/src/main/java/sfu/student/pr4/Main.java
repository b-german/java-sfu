package sfu.student.pr4;

import java.util.Scanner;
import sfu.student.menu.cli.api.UserInputManager;

public class Main {

  private static final String HEADER = """
      Задание: Пр 4
      Необходимо описать интерфейс, содержащий одну функцию
      int count(int number);
      Данный интерфейс позволяет вычислять характеристики чисел. Необходимо реализовать два класса
      для этого интерфейса. Первый класс должен вычислить количество символов в десятичной записи
      для этого числа. Второй класс должен вычислить количество различных простых чисел, являющихся
      множителями данного числа. Необходимо, чтобы приложение запросило у пользователя число и
      выдало статистические сведения для обоих классов.
      """;

  public static void main(String[] args) {
    Scanner inputScanner = new Scanner(System.in);
    UserInputManager inputManager = new UserInputManager(inputScanner);
    System.out.println(HEADER);

    Integer integer = inputManager.getInteger("Введите число: ");

    System.out.printf(
        "Количество символов в десятичной записи введённого числа равно %s%n",
        new LengthCounter().count(integer));

    System.out.printf(
        "Количество различных простых чисел, являющихся множителями данного числа, равно %s%n",
        new PrimeCounter().count(integer));
  }
}