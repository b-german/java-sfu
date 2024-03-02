package sfu.student.pr1;

import java.util.Random;

public class RandomTextGenerator {

  public static String generateRandomText() {
    int length = (int) (100 + Math.random() * 1000);
    System.out.println("Длина сгенерированного случайного текста: %s".formatted(length));

    StringBuilder sb = new StringBuilder(length);
    new Random().ints(length, 33, 126)
        .mapToObj(integer -> (char) integer)
        .forEach(sb::append);
    System.out.println("Текст: %s".formatted(sb.toString()));

    return sb.toString();
  }

}
