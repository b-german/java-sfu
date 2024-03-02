package sfu.student.pr1;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public class InputStringByLengthSorter {

  public static final String REGEXP_WORDS = "[^A-Za-zА-я]+";

  private String inputString;
  private String[] stringArray;

  public void acceptInputString(String string) {
    inputString = string;

    if (isInputStringEmpty()) {
      System.out.println("Ничего не введено! Попробуйте ещё раз.");
    }

    stringArray = null;
  }

  public void sortInputString() {
    if (isInputStringEmpty()) {
      System.out.println("Алгоритм не может быть выполнен без введенных данных!");
      return;
    }
    stringArray = inputString.split(REGEXP_WORDS);
    Arrays.sort(stringArray, Comparator.comparing(String::length));
  }

  public void printResult() {
    if (isInputStringEmpty()) {
      System.out.println("Результат не может быть выведен без введенных данных!");
      return;
    }
    if (Objects.isNull(stringArray)) {
      System.out.println("Вывод результата не может быть осуществлен без выполнения алгоритма!");
      return;
    }

    System.out.println(Arrays.toString(stringArray));

  }

  private boolean isInputStringEmpty() {
    return Objects.isNull(inputString) || inputString.isBlank();
  }

}
