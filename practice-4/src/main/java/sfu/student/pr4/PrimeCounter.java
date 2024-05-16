package sfu.student.pr4;

import java.util.HashSet;
import java.util.Set;

public class PrimeCounter implements Counter {

  @Override
  public int count(int number) {
    Counter func = (int num) -> {
      Set<Integer> primes = new HashSet<>();
      for (int i = 2; i <= num; i++) {
        if (num % i == 0) {
          while (num % i == 0) {
            num /= i;
          }
          primes.add(i);
        }
      }
      return primes.size();
    };
    return func.count(number);
  }
}
