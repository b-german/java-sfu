package sfu.student.pr7;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.DoubleSummaryStatistics;
import java.util.IntSummaryStatistics;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import sfu.student.menu.cli.api.MenuManager;
import sfu.student.menu.cli.api.UserInputManager;
import sfu.student.pr7.exceptions.InvalidTreeCreationException;
import sfu.student.pr7.exceptions.InvalidTreeFieldException;
import sfu.student.pr7.exceptions.NoSuchMenuOption;
import sfu.student.pr7.exceptions.NoUserInputDataException;

public class Main {

  private static final String HEADER = """
      Пр7
      За основу взять 5-ую практическую работу. Необходимо добавить новые пункты меню (или изменить
      имеющиеся), которые реализовывали бы следующие функции (все операции должны выполняться при
      помощи stream):

      -Создание потока из массива/списка объектов и вывод их на экран.
      -Фильтрация объектов по некоторому признаку, введенному пользователем (Достаточно одного.
      Например, фильтрация товаров, цена которых выше заданной. Цену задает пользователь).
      -Изъятие из массива/списка дубликатов.
      -Демонстрация умения работать с операциями сведения с накоплением (Например,
      сумма всех товаров массива/списка).
      -Демонстрация умения работать с типом Optional (Продумать, обосновать и предложить пример
      самостоятельно).
      -Сгруппировать объекты по некоторому полю (на выбор студента) и вывести число элементов
      в группе.
      -Демонстрация умения работать с SummaryStatistics.
      -Загрузка и сохранение данных в файловой системе.
      """;

  private static final Logger logger = Logger.getLogger("sfu.student.pr7.Main");

  public static void main(String[] args) {
    //подавление возможных исключений при использовании try-with-resources
    try (Scanner inputScanner = new Scanner(System.in)) {
      TreeCollection trees = new TreeCollection();
      UserInputManager inputManager = new UserInputManager(inputScanner);
      System.out.println(HEADER);

      boolean isRequiredDataPreparation = MenuManager.getBooleanMenu(inputManager)
          .getChoice("Предзаполнить сад деревьями?")
          .getValue();
      if (isRequiredDataPreparation) {
        trees.generateRandomData(50);
      }

      boolean exitFlag = false;
      while (!exitFlag) {
        MenuManager<Practice2MenuExt> menuManager = new MenuManager<>(Practice2MenuExt.values());
        Practice2MenuExt userChoice = menuManager.getChoice(inputScanner);
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
            case STREAM_API_OPTIONS -> runStreamApiDemoMenu(trees, inputManager);
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

  private static void runStreamApiDemoMenu(TreeCollection trees, UserInputManager inputManager) {
    boolean exitFlag = false;
    MenuManager<StreamApiDemoOptions> menuManager = new MenuManager<>(
        StreamApiDemoOptions.values(), inputManager);
    while (!exitFlag) {
      StreamApiDemoOptions userChoice = menuManager.getChoice();

      try {
        switch (userChoice) {
          case DISPLAY -> trees.stream().map(Object::toString).forEach(logger::info);
          case DISTINCT -> trees.replaceList(trees.stream().distinct().toList());
          case SUM -> trees.stream()
              .map(Tree::getHeightInM)
              .reduce(Float::sum)
              .ifPresentOrElse(
                  totalHeight -> logger.info("Высота всех деревьев равна %f".formatted(totalHeight)),
                  () -> logger.warning("Невозможно рассчитать высоту, деревьев нет!"));
          case GET_BY_INDEX_OPTIONAL -> {
            // Демонстрация умения работать с типом Optional
            Optional<Tree> treeOptional = trees.findByIndex(
                inputManager.getInteger("Введите порядковый номер дерева: ") - 1);
            treeOptional.map(Tree::toString).ifPresentOrElse(
                logger::info,
                () -> logger.warning("По указанному порядковому номеру нет дерева!"));
          }
          case GROUP_BY -> {
            Map<String, Long> groupedByTypeCount = trees.stream()
                .collect(Collectors.groupingBy(Tree::getType, Collectors.counting()));
            groupedByTypeCount.entrySet().stream()
                .map(entry -> "Вид: %s, количество: %s".formatted(entry.getKey(), entry.getValue()))
                .forEach(logger::info);
          }
          case SUMMARY_STATISTICS -> {
            IntSummaryStatistics diameterSummary = trees.stream()
                .collect(Collectors.summarizingInt(Tree::getDiameterInCm));
            DoubleSummaryStatistics heightSummary = trees.stream()
                .collect(Collectors.summarizingDouble(Tree::getHeightInM));
            String template = """
                Сводная статистика по %s:
                Деревьев: %s, минимум: %s, максимум: %s, среднее: %s, сумма: %s
                """;
            logger.info(() -> template.formatted("диаметру",
                diameterSummary.getCount(),
                diameterSummary.getMin(),
                diameterSummary.getMax(),
                diameterSummary.getAverage(),
                diameterSummary.getSum()));

            logger.info(() -> template.formatted("высоте",
                heightSummary.getCount(),
                heightSummary.getMin(),
                heightSummary.getMax(),
                heightSummary.getAverage(),
                heightSummary.getSum()));
          }
          case SAVE_TO_DISK -> {
            String filename = inputManager.getString("Введите название файла сохранения: ");
            try (FileOutputStream fos = new FileOutputStream(filename);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
              oos.writeObject(trees);
            } catch (IOException e) {
              logger.throwing(Main.class.getSimpleName(), "Save_to_disk", e);
            }
          }
          case LOAD_FROM_DISK -> {
            String filename = inputManager.getString("Введите название файла с коллекцией: ");
            if (Files.notExists(Path.of(filename))) {
              logger.warning("Не найден файл по пути %s!".formatted(filename));
              continue;
            }
            try (FileInputStream fis = new FileInputStream(filename);
                ObjectInputStream ois = new ObjectInputStream(fis)) {
              trees = (TreeCollection) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
              logger.throwing(Main.class.getSimpleName(), "Load_from_disk", e);
            }

          }
          case FILTER -> {
            Integer integer = inputManager.getInteger();
            trees.stream()
                .filter(tree -> tree.getDiameterInCm() > integer)
                .map(Object::toString)
                .forEach(logger::info);
          }
          case EXIT -> exitFlag = true;
          default -> throw new IllegalStateException("Unexpected value: " + userChoice);
        }
      } catch (NoUserInputDataException e) {
        logger.warning(e.getMessage());
      }
    }
  }
}