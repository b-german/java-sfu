package sfu.student.pr8;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import sfu.student.pr8.ui.CashierCard;

/**
 * Класс управляющий графическим интерфейсом супермаркета
 */
public class SupermarketManagerFrame extends JFrame {

  private static final String CASHIER_LABEL_TEMPLATE = "Количество кассиров: %s";
  private static final String TIME_PER_CUSTOMER_LABEL_TEMPLATE = "Время на обслуживание: %s мс";
  private final JPanel cashierPanel = new JPanel();
  private final HashMap<Cashier, CashierCard> cashierCards = new HashMap<>();
  private final JLabel cashiersCountLabel;
  private final JLabel timePerCustomerLabel;
  private Supermarket supermarket;
  private final JButton startButton;
  private final JButton addCustomerButton;
  private final JMenuItem importMenuItem;
  private int cashierCount = 5;
  private int timePerCustomer = 3000;

  /**
   * Создает класс GUI супермаркета
   */
  public SupermarketManagerFrame() {
    super("Супермаркет");
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setSize(1200, 800);
    setLayout(new BorderLayout());

    JPanel controlPanel = new JPanel();
    cashiersCountLabel = new JLabel(CASHIER_LABEL_TEMPLATE.formatted(cashierCount));
    timePerCustomerLabel = new JLabel(TIME_PER_CUSTOMER_LABEL_TEMPLATE.formatted(timePerCustomer));
    controlPanel.add(cashiersCountLabel);
    controlPanel.add(timePerCustomerLabel);

    startButton = new JButton("Открыть супермаркет");
    controlPanel.add(startButton);
    startButton.addActionListener(e -> open());

    addCustomerButton = new JButton("Впустить клиента");
    addCustomerButton.setEnabled(false);
    controlPanel.add(addCustomerButton);
    addCustomerButton.addActionListener(e -> letInCustomer());

    add(controlPanel, BorderLayout.SOUTH);

    cashierPanel.setLayout(new GridLayout(1, 0));
    JScrollPane scrollPane = new JScrollPane(cashierPanel);
    scrollPane.setPreferredSize(new Dimension(0, 150));
    add(scrollPane, BorderLayout.NORTH);

    importMenuItem = new JMenuItem("Импортировать");
    initTopBarMenu();

    setVisible(true);
  }

  private void initTopBarMenu() {
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("Настройки");
    JMenuItem exportMenuItem = new JMenuItem("Экспортировать");
    importMenuItem.addActionListener(e -> importSettings());
    exportMenuItem.addActionListener(e -> exportSettings());
    fileMenu.add(importMenuItem);
    fileMenu.add(exportMenuItem);
    menuBar.add(fileMenu);
    setJMenuBar(menuBar);
  }

  private void importSettings() {
    JFileChooser fileChooser = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter("XML файлы", "xml");
    fileChooser.setFileFilter(filter);
    int returnValue = fileChooser.showOpenDialog(this);

    if (returnValue == JFileChooser.APPROVE_OPTION) {
      SupermarketSettings supermarketSettings = XmlUtils.importFromXml(
          fileChooser.getSelectedFile());
      Optional.ofNullable(supermarketSettings.cashierCount())
          .ifPresent(this::setCashiersCount);
      Optional.ofNullable(supermarketSettings.timePerCustomer())
          .ifPresent(this::setTimePerCustomer);
    }
  }

  private void exportSettings() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setSelectedFile(new File("settings.xml"));
    int returnValue = fileChooser.showSaveDialog(this);
    if (returnValue == JFileChooser.APPROVE_OPTION) {
      XmlUtils.exportToXml(new SupermarketSettings(cashierCount, timePerCustomer),
          fileChooser.getSelectedFile());
    }
  }

  private void open() {
    if (Objects.isNull(supermarket)) {
      supermarket = new Supermarket(cashierCount, timePerCustomer, this);
      startButton.setEnabled(false);
      importMenuItem.setEnabled(false);
      addCustomerButton.setEnabled(true);
    }
  }

  private void letInCustomer() {
    if (supermarket != null) {
      supermarket.generateCustomer();
    }
  }

  /**
   * Инициализирует карточку кассира
   *
   * @param cashier кассир
   */
  public void initCashierCard(Cashier cashier) {
    CashierCard cashierCard = new CashierCard();

    cashierCards.put(cashier, cashierCard);
    cashierPanel.add(cashierCard);
    cashierPanel.revalidate();
    cashierPanel.repaint();
  }

  /**
   * Обновляет список стоящих в очереди к кассиру на его карточке
   *
   * @param queue   очередь
   * @param cashier кассир
   */
  public void updateCustomersQueue(BlockingQueue<Customer> queue, Cashier cashier) {
    cashierCards.get(cashier).updateQueue(queue);
  }

  /**
   * Устанавливает состояние обслуживания кассиру на его карточке
   *
   * @param name    имя обслуживаемого
   * @param cashier кассир
   */
  public void setCashierServing(String name, Cashier cashier) {
    cashierCards.get(cashier).setServing(name);
  }

  /**
   * Устанавливает состояние бездействия кассиру на его карточке
   *
   * @param cashier кассир
   */
  public void setCashierIdle(Cashier cashier) {
    cashierCards.get(cashier).setIdle();
  }

  /**
   * Заупскает прогресс-бар обслуживания клиента на карточке кассира
   *
   * @param cashier  кассир
   * @param duration длительность обслуживания
   */
  public void runProgressBar(Cashier cashier, int duration) {
    cashierCards.get(cashier).runProgressBar(duration);
  }

  private void setCashiersCount(Integer integer) {
    cashierCount = integer;
    SwingUtilities.invokeLater(() -> cashiersCountLabel.setText(
        CASHIER_LABEL_TEMPLATE.formatted(cashierCount)));
  }

  private void setTimePerCustomer(Integer integer) {
    timePerCustomer = integer;
    SwingUtilities.invokeLater(() -> timePerCustomerLabel.setText(
        TIME_PER_CUSTOMER_LABEL_TEMPLATE.formatted(timePerCustomer)));

  }
}
