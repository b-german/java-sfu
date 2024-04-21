package sfu.student;

import java.util.Objects;

public class Tree {

  private static final String NOT_FILLED = "Не заполнено";

  private int diameterInCm;
  private float heightInM;
  private String type;
  private String whatGrowsOnTree;

  public Tree() {
  }

  public Tree(int diameter, float height, String type, String whatGrowsOnTree) {
    setDiameterInCm(diameter);
    setHeightInM(height);
    setType(type);
    setWhatGrowsOnTree(whatGrowsOnTree);
  }

  public int getDiameterInCm() {
    return diameterInCm;
  }

  public void setDiameterInCm(int diameterInCm) {
    if (isValidDiameter(diameterInCm)) {
      this.diameterInCm = diameterInCm;
    }
  }

  public float getHeightInM() {
    return heightInM;
  }

  public void setHeightInM(float heightInM) {
    if (isValidHeight(heightInM)) {
      this.heightInM = heightInM;
    }
  }

  public String getType() {
    return Objects.nonNull(type) ? type : NOT_FILLED;
  }

  public void setType(String type) {
    if (isValidString(type)) {
      this.type = type;
    }
  }

  public String getWhatGrowsOnTree() {
    return Objects.nonNull(whatGrowsOnTree) ? whatGrowsOnTree : NOT_FILLED;
  }

  public void setWhatGrowsOnTree(String whatGrowsOnTree) {
    if (isValidString(whatGrowsOnTree)) {
      this.whatGrowsOnTree = whatGrowsOnTree;
    }
  }

  @Override
  public String toString() {
    return String.format(
        "Дерево вида '%s', на нём растут '%s', высота %.2fм, диаметр %sсм, объём %.2fсм2",
        getType(),
        getWhatGrowsOnTree(),
        getHeightInM(),
        getDiameterInCm(),
        getWoodVolume()
    );
  }

  public Double getWoodVolume() {
    final float h = heightInM * 100;
    final double r2 = Math.pow((double) diameterInCm / 2, 2);
    return Math.PI * r2 * h;
  }

  public void setByFieldName(String field, Object value) {
    field = field.toLowerCase();
    switch (field) {
      case "диаметр" -> setDiameterInCm((Integer) value);
      case "высота" -> setHeightInM((Float) value);
      case "вид" -> setType((String) value);
      case "плод" -> setWhatGrowsOnTree((String) value);
      default -> System.out.println(String.format("Не найдено поле %s!", field));
    }
  }

  private boolean isValidString(String stringVal) {
    if (stringVal.isBlank()) {
      System.out.println("Передана пустая строка!");
      return false;
    }
    return true;
  }

  private boolean isValidHeight(Float floatVal) {
    final float tallestTreeM = 115.85F;
    if (floatVal <= 0 || floatVal > tallestTreeM) {
      final String msg = """
          Передана некорректная высота '%s'.
          Высота должна быть больше 0 и не выше самой высокой секвойи 115.85м.
          """;
      System.out.println(String.format(msg, floatVal));
      return false;
    }
    return true;
  }

  private boolean isValidDiameter(Integer diameter) {
    final int biggestTreeDiameterCm = 10640;
    if (diameter <= 0 || diameter > biggestTreeDiameterCm) {
      System.out.println(String.format("""
          Передан некорретный диаметр '%s'.
          Диаметр должен быть больше 0 и не больше самого большого баобаба 10640см.
          """, diameter));
      return false;
    }
    return true;
  }
}
