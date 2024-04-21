package sfu.student.pr2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TreeCollection {

  private final List<Tree> trees = new ArrayList<>();

  void addTree() {
    trees.add(new Tree());
    System.out.println("Добавлено незаполненное дерево.");
  }

  void addTree(Integer diameter, Float height, String type, String leafType) {
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

  public void display() {
    if (trees.isEmpty()) {
      System.out.println("Деревьев нет!");
      return;
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
}
