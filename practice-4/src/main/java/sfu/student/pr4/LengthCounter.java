package sfu.student.pr4;

public class LengthCounter implements Counter {

  @Override
  public int count(int number) {
    Counter func = (int num) -> String.valueOf(num).length();
    return func.count(number);
  }
}
