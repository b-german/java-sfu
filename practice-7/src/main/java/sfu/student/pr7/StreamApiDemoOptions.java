package sfu.student.pr7;

import sfu.student.menu.cli.api.MenuOption;

public enum StreamApiDemoOptions implements MenuOption<StreamApiDemoOptions> {
  DISPLAY("1", "Показать деревья"),
  DISTINCT("2", "Убрать дубли деревьев"),
  SUM("3", "Вывести сумму высоты деревьев"),
  GET_BY_INDEX_OPTIONAL("4", "Вывести дерево по индексу"),
  GROUP_BY("5", "Подсчитать количество деревьев каждого вида"),
  SUMMARY_STATISTICS("6", "Сводная статистика"),
  SAVE_TO_DISK("7", "Сохранить коллекцию в файл"),
  LOAD_FROM_DISK("8", "Загрузить коллекцию из файла"),
  FILTER("9", "Вывести деревья с диаметром больше заданного"),
  EXIT("10", "Вернуться в общее меню");

  private final String code;
  private final String text;

  StreamApiDemoOptions(String code, String text) {
    this.code = code;
    this.text = text;
  }

  @Override
  public String getCode() {
    return code;
  }

  @Override
  public String getText() {
    return text;
  }
}
