package sfu.student;

import java.util.Scanner;
import sfu.student.menu.cli.api.MenuManager;

public class Main {

  private static final String HEADER = """
      Задание: Пр 2
      Дерево
      """;

  public static void main(String[] args) {
    Scanner inputScanner = new Scanner(System.in);
    TreeCollection trees = new TreeCollection();
    UserInputManager inputManager = new UserInputManager(inputScanner);
    System.out.println(HEADER);

    boolean exitFlag = false;
    while (!exitFlag) {
      MenuManager<Practice2Menu> menuManager = new MenuManager<>(Practice2Menu.values());
      Practice2Menu userChoice = menuManager.getChoice(inputScanner);
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

          trees.addTree(diameter, height, treeType, whatGrows);
        }
        case EDIT -> {
          System.out.println("Порядковый номер дерева?");
          Integer displayedIndex = inputManager.getInteger();

          System.out.println("Какое поле редактируем? Доступные поля: диаметр, высота, вид, плод");
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

          trees.editTree(displayedIndex - 1, field, value);
        }
        case DISPLAY -> trees.display();
        case SORT -> {
          System.out.println(
              "По какому полю сортируем? Доступные поля: диаметр, высота, вид, плод");
          String field = inputManager.getString();

          trees.sortByField(field);
        }
        case EXIT -> exitFlag = true;
      }
    }
  }
}