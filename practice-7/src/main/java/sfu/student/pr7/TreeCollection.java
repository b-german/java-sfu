package sfu.student.pr7;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import sfu.student.pr7.exceptions.InvalidTreeCreationException;
import sfu.student.pr7.exceptions.NoUserInputDataException;

public class TreeCollection implements Serializable {

  private static final String[] TREE_TYPES = {
      "Сосна",
      "Ель",
      "Тополь",
      "Береза",
      "Баобаб",
      "Яблоня",
      "Кедр",
      "Пальма",
      "Секвойя",
      "Лиственница",
      "Дуб"
  };
  private static final String[] GROWABLE_TYPES = {
      "Яблоко",
      "Груша",
      "Вишня",
      "Слива",
      "Персик",
      "Манго",
      "Апельсин",
      "Лимон",
      "Абрикос",
      "Ананас"
  };
  private final Random random = new Random();
  private final List<Tree> trees = new ArrayList<>();

  void addTree() {
    trees.add(new Tree());
    System.out.println("Добавлено незаполненное дерево.");
  }

  void addTree(Integer diameter, Float height, String type, String leafType)
      throws InvalidTreeCreationException {
    trees.add(new Tree(diameter, height, type, leafType));
  }

  void editTree(Integer realIndex, String field, Object value) {
    if (!getByIndexValid(realIndex)) {
      return;
    }
    Tree tree = trees.get(realIndex);
    tree.setByFieldName(field, value);
  }

  private boolean getByIndexValid(Integer index) {
    if (index < 0 || index > trees.size()) {
      System.out.println(String.format("Нет дерева с номером %s!", index + 1));
      return false;
    }
    return true;
  }

  public void display() throws NoUserInputDataException {
    if (trees.isEmpty()) {
      throw new NoUserInputDataException("Деревьев нет!");
    }
    for (int i = 0; i < trees.size(); i++) {
      Tree tree = trees.get(i);
      System.out.println(String.format("%s) %s", i + 1, tree.toString()));
    }
  }

  public void sortByField(String field) {
    field = field.toLowerCase();
    switch (field) {
      case "диаметр" -> trees.sort(Comparator.comparing(Tree::getDiameterInCm));
      case "высота" -> trees.sort(Comparator.comparing(Tree::getHeightInM));
      case "вид" -> trees.sort(Comparator.comparing(Tree::getType));
      case "плод" -> trees.sort(Comparator.comparing(Tree::getWhatGrowsOnTree));
      default -> System.out.println(String.format("Не найдено поле %s!", field));
    }
  }

  public void generateRandomData(int i) {
    trees.addAll(IntStream.range(0, i)
        .mapToObj(this::generateTree)
        .toList());
  }

  public void replaceList(List<Tree> newTrees) {
    trees.clear();
    trees.addAll(newTrees);
  }

  public Stream<Tree> stream() throws NoUserInputDataException {
    if (trees.isEmpty()) {
      throw new NoUserInputDataException("Деревьев нет!");
    }
    return trees.stream();
  }

  private Tree generateTree(int index) {
    try {
      return new Tree(index + 1, index + 1f, getRandomTreeType(), getRandomGrowable());
    } catch (InvalidTreeCreationException e) {
      throw new RuntimeException("Ошибка при генерации дерева!", e);
    }
  }

  private String getRandomTreeType() {
    Optional<String> randomTypeOptional = Arrays.stream(TREE_TYPES)
        .skip(random.nextInt(TREE_TYPES.length))
        .findFirst();

    return randomTypeOptional.orElseThrow(
        () -> new RuntimeException("Случайный тип дерева не найден!"));
  }

  private String getRandomGrowable() {
    Optional<String> randomTypeOptional = Arrays.stream(GROWABLE_TYPES)
        .skip(random.nextInt(GROWABLE_TYPES.length))
        .findFirst();

    return randomTypeOptional.orElseThrow(
        () -> new RuntimeException("Случайный плод дерева не найден!"));
  }

  /**
   * Чтобы не бросать исключения, можно вернуть пустой объект Optional
   *
   * @param index - порядковый номер дерева
   * @return экземпляр <code>Tree</code>, обернутый в <code>Optional</code>, если имеется, иначе
   * {@link Optional#empty()}
   */
  public Optional<Tree> findByIndex(int index) {
    if (index < 0 || index >= trees.size()) {
      return Optional.empty();
    }
    return Optional.of(trees.get(index));
  }
}
