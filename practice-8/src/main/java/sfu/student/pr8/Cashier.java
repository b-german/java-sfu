package sfu.student.pr8;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


/**
 * Класс кассира супермаркета
 */
public class Cashier implements Runnable {

  private final BlockingQueue<Customer> customersLine;
  private final SupermarketManagerFrame ui;

  /**
   * Создает нового кассира
   *
   * @param ui the ui
   */
  public Cashier(SupermarketManagerFrame ui) {
    this.customersLine = new ArrayBlockingQueue<>(15, true);
    this.ui = ui;
  }

  /**
   * Принимает покупателя в очередь
   *
   * @param customer покупатель
   * @return добавлен ли покупатель в очередь
   */
  public boolean acceptCustomer(Customer customer) {
    boolean offered = customersLine.offer(customer);
    if (offered) {
      ui.updateCustomersQueue(customersLine, this);
    }
    return offered;
  }

  /**
   * Метод интерфейса Runnable. Точка входа для исполнения в отдельном потоке
   */
  @Override
  public void run() {
    while (true) {
      try {
        Customer customer = customersLine.take();
        ui.updateCustomersQueue(customersLine, this);

        ui.setCashierServing(customer.getName(), this);
        processCustomer(customer);
        ui.setCashierIdle(this);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }

  private void processCustomer(Customer customer) throws InterruptedException {
    int duration = customer.getProcessingTime();
    ui.runProgressBar(this, duration);
    Thread.sleep(duration);
    customer.makeCustomerServiced();
  }

}
