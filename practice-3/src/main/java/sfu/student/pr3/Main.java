package sfu.student.pr3;

import java.util.Scanner;
import sfu.student.menu.cli.api.BooleanMenuValue;
import sfu.student.menu.cli.api.MenuManager;
import sfu.student.menu.cli.api.UserInputManager;
import sfu.student.pr3.shapes.Polygon;
import sfu.student.pr3.shapes.PolygonCollection;
import sfu.student.pr3.shapes.Rectangle;
import sfu.student.pr3.shapes.Square;
import sfu.student.pr3.shapes.Triangle;
import sfu.student.validation.ValidationResult;

public class Main {

  private static final String HEADER = """
      Задание: Пр 3
      Многоугольник, прямоугольник, квадрат, треугольник.
      """;


  public static void main(String[] args) {
    Scanner inputScanner = new Scanner(System.in);
    PolygonCollection polygonCollection = new PolygonCollection();
    UserInputManager inputManager = new UserInputManager(inputScanner);
    System.out.println(HEADER);

    boolean exitFlag = false;
    while (!exitFlag) {
      MenuManager<PolygonsMainMenu> menuManager = new MenuManager<>(PolygonsMainMenu.values(),
          inputManager);
      PolygonsMainMenu userChoice = menuManager.getChoice();
      switch (userChoice) {
        case ADD -> {
          MenuManager<PolygonsCreationMenu> polygonCreationMenu = new MenuManager<>(
              PolygonsCreationMenu.values(), inputManager);
          MenuManager<BooleanMenuValue> yesNoMenu = MenuManager.getBooleanMenu(inputManager);
          PolygonsCreationMenu figure = polygonCreationMenu.getChoice("Выберите фигуру:");
          boolean fillValues = yesNoMenu.getChoice("Заполнить значения полей фигуры?")
              .getValue();

          Polygon polygon = null;
          switch (figure) {
            case POLYGON -> polygon =
                fillValues ? new Polygon(inputManager.getInteger("Введите количество вершин:"),
                    inputManager.getString("Введите название фигуры:")) : new Polygon();
            case RECTANGLE -> polygon =
                fillValues ? new Rectangle(inputManager.getString("Введите название фигуры:"),
                    inputManager.getInteger("Введите длину:"),
                    inputManager.getInteger("Введите ширину:"),
                    inputManager.getString("Введите комментарий к фигуре:")) : new Rectangle();
            case SQUARE -> polygon =
                fillValues ? new Square(inputManager.getString("Введите название фигуры:"),
                    inputManager.getInteger("Введите длину стороны:"),
                    inputManager.getString("Введите цвет фигуры:")) : new Square();
            case TRIANGLE -> polygon =
                fillValues ? new Triangle(inputManager.getString("Введите название фигуры:"),
                    inputManager.getInteger("Введите длину основания:"),
                    inputManager.getString("Введите цвет фигуры:")) : new Triangle();
            default -> System.out.println("Unreachable code that could have been omitted");
          }

          ValidationResult validationResult = polygon.getValidationResult();
          if (!validationResult.isValid()) {
            System.out.println("Невозможно добавить фигуру! Указанные значения некорректны!");
            System.out.println(validationResult.get());
            continue;
          }

          polygonCollection.addPolygon(polygon);
        }
        case REMOVE -> {
          if (isEmptyPolygonCollection(polygonCollection)) {
            continue;
          }
          Integer humanIdx = inputManager.getInteger("Введите порядковый номер фигуры:");
          if (isInvalidIndex(polygonCollection, humanIdx)) {
            continue;
          }
          polygonCollection.removePolygon(humanIdx - 1);
        }
        case DISPLAY -> {
          if (isEmptyPolygonCollection(polygonCollection)) {
            continue;
          }
          System.out.println(polygonCollection.getAllAsString());
        }
        case COMPARE -> {
          if (isEmptyPolygonCollection(polygonCollection)) {
            continue;
          }
          Integer humanIdxFirst = inputManager.getInteger(
              "Введите порядковый номер первой фигуры:");
          if (isInvalidIndex(polygonCollection, humanIdxFirst)) {
            continue;
          }
          Integer humanIdxSecond = inputManager.getInteger(
              "Введите порядковый номер второй фигуры:");
          if (isInvalidIndex(polygonCollection, humanIdxSecond)) {
            continue;
          }
          if (polygonCollection.isEqualPolygons(humanIdxFirst - 1, humanIdxSecond - 1)) {
            System.out.println("Фигуры равны!");
          } else {
            System.out.println("Фигуры не равны!");
          }
        }
        case EXIT -> exitFlag = true;
        default -> System.out.println("Unreachable code that could have been omitted");
      }
    }
  }

  private static boolean isInvalidIndex(PolygonCollection polygonCollection, Integer humanIdx) {
    if (polygonCollection.isOutOfBoundsIdx(humanIdx - 1)) {
      System.out.println("Нет фигуры с порядковым номером %s".formatted(humanIdx));
      return true;
    }
    return false;
  }

  private static boolean isEmptyPolygonCollection(PolygonCollection polygonCollection) {
    if (polygonCollection.isEmpty()) {
      System.out.println(
          "Результат не может быть выведен без обработки введённых данных. Коллекция фигур пуста!");
      return true;
    }
    return false;
  }
}