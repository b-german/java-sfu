package sfu.student.pr8;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Класс покупателя
 */
public class Customer implements Runnable {

  private static final Random RANDOM = new Random();
  private final String name;
  private final List<Cashier> cashiers;
  private CountDownLatch servicedSignal;
  private boolean serviced = false;
  private final int requiredTime;

  /**
   * Содает нового покупателя
   *
   * @param name         имя покупателя
   * @param cashiers     список кассиров
   * @param requiredTime требуемое время в мс
   */
  public Customer(String name, List<Cashier> cashiers, int requiredTime) {
    this.name = name;
    this.cashiers = cashiers;
    this.requiredTime = requiredTime;
  }

  /**
   * Метод интерфейса Runnable. Точка входа для исполнения в отдельном потоке
   */
  @Override
  public void run() {
    Cashier cashier = cashiers.get(RANDOM.nextInt(cashiers.size()));
    servicedSignal = new CountDownLatch(1);
    cashier.acceptCustomer(this);
    while (!serviced) {
      try {
        serviced = servicedSignal.await(100, TimeUnit.MILLISECONDS);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }

  /**
   * Возвращает имя покупателя
   *
   * @return имя name
   */
  public String getName() {
    return name;
  }

  /**
   * Сигнал, обозначающий, что покупателя обслужили
   */
  public void makeCustomerServiced() {
    servicedSignal.countDown();
  }

  /**
   * Возвращает время, за которое покупателя можно обслужить
   *
   * @return время в мс
   */
  public int getProcessingTime() {
    return requiredTime;
  }
}
