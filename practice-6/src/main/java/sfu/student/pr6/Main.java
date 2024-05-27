package sfu.student.pr6;

import java.util.NoSuchElementException;
import java.util.Scanner;
import sfu.student.menu.cli.api.MenuManager;
import sfu.student.menu.cli.api.UserInputManager;

public class Main {

  private static final String HEADER = """
      Задание: Пр 6
      Необходимо реализовать свою собственную обобщенную коллекцию данных с методами указанными
      в варианте.
      Разработать простейшее приложение, пункты меню которого позволяют демонстрировать
      разработанные методы класса-коллекции. Предусмотреть и произвести обработку потенциальных
      исключительных ситуаций.
      Демонстрационное приложение должно работать как со ссылочными данными (например, String),
      так и примитивными (например, int) по выбору пользователя.
            
      Вариант 2. Стек. Операции над стеком:
      - проверить, стек пуст/не пуст;
      - втолкнуть элемент;
      - вытолкнуть элемент;
      - получить вершину стека;
      - обменять значения двух верхних элементов стека;
      - извлечь из стека первое вхождение передаваемого значения;
      - вывод стека на экран.
      """;

  public static void main(String[] args) {
    System.out.println(HEADER);

    Scanner inputScanner = new Scanner(System.in);
    UserInputManager inputManager = new UserInputManager(inputScanner);
    MenuManager<ObjectOptions> objectOptionsMenu = new MenuManager<>(
        ObjectOptions.values(),
        inputManager
    );
    MenuManager<ArrayStackOptions> arrayStackOptionsMenu = new MenuManager<>(
        ArrayStackOptions.values(),
        inputManager
    );

    ObjectOptions objectType = objectOptionsMenu.getChoice("Выберите тип данных: ");
    ArrayStack<? super Object> stack = new ArrayStack<>();
    boolean exitFlag = false;
    while (!exitFlag) {
      ArrayStackOptions action = arrayStackOptionsMenu.getChoice(
          "Выберите действие с коллекцией:");

      switch (action) {
        case IS_EMPTY ->
            System.out.println("Стек %s!".formatted(stack.isEmpty() ? "пуст" : "не пуст"));
        case PUSH -> {
          Object value = getValue(objectType, inputManager);
          try {
            stack.push(value);
          } catch (IllegalArgumentException exception) {
            System.out.println("Передано пустое значение!");
          }
        }
        case POP -> wrapNoSuchElementException(() -> System.out.println(stack.pop()));
        case PEEK -> wrapNoSuchElementException(() -> System.out.println(stack.peek()));
        case SWAP -> {
          try {
            stack.swap();
            System.out.println("Два верхних элемента обменялись значениями!");
          } catch (IllegalStateException exception) {
            System.out.println(
                "Невозможно выполнить операцию! %s".formatted(exception.getMessage()));
          }
        }
        case REMOVE -> {
          System.out.println("Введите значение для извлечения.");
          Object value = getValue(objectType, inputManager);
          System.out.println(
              "Значение %s было извлечено.".formatted(stack.remove(value) ? "" : "не"));
        }
        case FIND -> {
          System.out.println("Введите значение для поиска в стеке. 0 - значение не найдено.");
          Object value = getValue(objectType, inputManager);
          System.out.println("Индекс %s.".formatted(stack.findIndex(value) + 1));
        }
        case PRINT -> wrapNoSuchElementException(stack::printStack);
        case EXIT -> exitFlag = true;
      }
    }
  }

  private static void wrapNoSuchElementException(Runnable operation) {
    try {
      operation.run();
    } catch (NoSuchElementException exception) {
      System.out.println("Невозможно выполнить операцию, стек пуст!");
    }
  }

  private static Object getValue(ObjectOptions objectType, UserInputManager inputManager) {
    return switch (objectType) {
      case SCALAR_INTEGER -> inputManager.getInteger("Введите целое число: ");
      case OBJECT_STRING -> inputManager.getString("Введите строку: ");
    };
  }
}