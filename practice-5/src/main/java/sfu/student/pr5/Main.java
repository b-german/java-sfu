package sfu.student.pr5;

import java.util.Scanner;
import java.util.logging.Logger;
import sfu.student.menu.cli.api.MenuManager;
import sfu.student.menu.cli.api.UserInputManager;
import sfu.student.pr5.exceptions.InvalidTreeCreationException;
import sfu.student.pr5.exceptions.InvalidTreeFieldException;
import sfu.student.pr5.exceptions.NoSuchMenuOption;
import sfu.student.pr5.exceptions.NoUserInputDataException;

public class Main {

  private static final String HEADER = """
      Задание: Пр 5
      Внести изменения в программный код, созданный при выполнении практической работы №2 таким
      образом чтобы проверка входных параметров (setter-ы и конструкторы) производилась при помощи
      исключений. Продемонстрировать умение пользоваться исключениями, при этом в результате
      перехвата исключений должна быть соответствующая реакция/сообщение на интерфейсе пользователя.
            
      В работе должно быть не менее трех примеров использования исключений:
      1) простой перехват исключения;
      2) повторное генерирование и связывание в цепочку;
      3) подавление исключения.
            
      Также необходимо продемонстрировать применение "утверждений" (достаточно одного примера)
      и "логирования" (достаточно одного примера).
      """;

  private static final Logger logger = Logger.getLogger("sfu.student.pr5.Main");

  public static void main(String[] args) {
    //подавление возможных исключений при использовании try-with-resources
    try (Scanner inputScanner = new Scanner(System.in)) {

      TreeCollection trees = new TreeCollection();
      UserInputManager inputManager = new UserInputManager(inputScanner);
      System.out.println(HEADER);

      boolean exitFlag = false;
      while (!exitFlag) {
        MenuManager<Practice2Menu> menuManager = new MenuManager<>(Practice2Menu.values());
        Practice2Menu userChoice = menuManager.getChoice(inputScanner);
        try {
          switch (userChoice) {
            case ADD_TREE -> trees.addTree();
            case ADD_DETAILED_TREE -> {
              System.out.println("Вид дерева?");
              String treeType = inputManager.getString();

              System.out.println("Что растет на дереве?");
              String whatGrows = inputManager.getString();

              System.out.println("Высота дерева в метрах? Например, 5,12");
              Float height = inputManager.getFloat();

              System.out.println("Диаметр дерева в сантиметрах? Например, 120");
              Integer diameter = inputManager.getInteger();

              try {
                trees.addTree(diameter, height, treeType, whatGrows);
              } catch (InvalidTreeCreationException e) {
                logger.info("Невозможно добавить дерево! %s %s".formatted(e.getMessage(),
                    e.getCause().getMessage()));
              }
            }
            case EDIT -> {
              System.out.println("Порядковый номер дерева?");
              Integer displayedIndex = inputManager.getInteger();

              System.out.println(
                  "Какое поле редактируем? Доступные поля: диаметр, высота, вид, плод");
              String field = inputManager.getString();

              System.out.println("На какое меняем?");
              Object value;
              switch (field) {
                case "вид", "плод" -> value = inputManager.getString();
                case "высота" -> value = inputManager.getFloat();
                case "диаметр" -> value = inputManager.getInteger();
                default -> {
                  System.out.println("Не найдено поле '%s'".formatted(field));
                  continue;
                }
              }

              try {
                trees.editTree(displayedIndex - 1, field, value);
              } catch (InvalidTreeFieldException e) {
                logger.info(
                    "При редактировании поля возникла ошибка! %s".formatted(e.getMessage()));
              }
            }
            case DISPLAY -> {
              try {
                trees.display();
              } catch (NoUserInputDataException e) {
                //простой перехват исключения
                logger.info(e.getMessage());
              }
            }
            case SORT -> {
              System.out.println(
                  "По какому полю сортируем? Доступные поля: диаметр, высота, вид, плод");
              String field = inputManager.getString();

              trees.sortByField(field);
            }
            case EXIT -> exitFlag = true;
            default -> throw new NoSuchMenuOption(
                "Обработка пункта меню '%s' не реализована!".formatted(userChoice.getText()));
          }
        } catch (NoSuchMenuOption exception) {
          //подавление исключения вручную
          RuntimeException runtimeException = new RuntimeException("Нет такой опции в меню!");
          runtimeException.addSuppressed(exception);
          throw runtimeException;
        }
      }
    } catch (Exception e) {
      logger.info("Поймано исключение! %s".formatted(e.getMessage()));
      for (Throwable suppressed : e.getSuppressed()) {
        logger.info(
            "В нём также было подавленное исключение! %s".formatted(suppressed.getMessage()));
      }
    }
  }
}