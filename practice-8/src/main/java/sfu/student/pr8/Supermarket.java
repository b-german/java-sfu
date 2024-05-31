package sfu.student.pr8;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Класс управляющий потоками покупателей и кассиров
 */
public class Supermarket {

  private static final Random RANDOM = new Random();
  private final List<Cashier> cashiers;

  private final Executor cashierExecutors;
  private final Executor customerExecutors;
  private final int timePerCustomer;

  /**
   * Создает супермаркет
   *
   * @param numCashiers     количество кассиров
   * @param timePerCustomer время обслуживания покупателя в мс
   * @param ui              менеджер интерфейса
   */
  public Supermarket(int numCashiers, int timePerCustomer, SupermarketManagerFrame ui) {
    if (Runtime.getRuntime().availableProcessors() <= numCashiers) {
      throw new IllegalArgumentException(
          "Невозможно задать количество кассиров больше чем доступно потоков процессора!");
    }
    this.timePerCustomer = timePerCustomer;
    cashierExecutors = Executors.newFixedThreadPool(numCashiers);
    customerExecutors = Executors.newCachedThreadPool();
    cashiers = new ArrayList<>();
    for (int i = 0; i < numCashiers; i++) {
      Cashier cashier = new Cashier(ui);
      ui.initCashierCard(cashier);
      cashiers.add(cashier);
      cashierExecutors.execute(cashier);
    }
  }

  /**
   * Создает поток нового покупателя
   */
  public void generateCustomer() {
    String[] names = {"Вася", "Петя", "Миша", "Анна", "Андрей", "Олег", "Саша", "Александр", "Катя",
        "Дима"};
    Customer customer = new Customer(names[RANDOM.nextInt(names.length)],
        cashiers,
        timePerCustomer);
    customerExecutors.execute(customer);
  }

}
