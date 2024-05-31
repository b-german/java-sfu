package sfu.student.pr8.ui;

import java.awt.BorderLayout;
import java.util.concurrent.BlockingQueue;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import sfu.student.pr8.Customer;

public class CashierCard extends JPanel {

  public static final String IDLE = "Простаивает";
  public static final String SERVING = "Обслуживает покупателя %s";
  private final JProgressBar progressBar;
  private final JLabel statusLabel;
  private final JTextArea queueArea;

  public CashierCard() {
    super(new BorderLayout());
    progressBar = new JProgressBar();
    statusLabel = new JLabel(IDLE);
    queueArea = new JTextArea(5, 20);
    queueArea.setEditable(false);

    add(statusLabel, BorderLayout.NORTH);
    add(progressBar, BorderLayout.CENTER);
    add(new JScrollPane(queueArea), BorderLayout.SOUTH);
  }

  public void updateQueue(BlockingQueue<Customer> queue) {
    SwingUtilities.invokeLater(() -> {
      StringBuilder sb = new StringBuilder();
      for (Customer inLine : queue) {
        sb.append(inLine.getName()).append(System.lineSeparator());
      }
      queueArea.setText(sb.toString());
    });
  }

  public void setIdle() {
    updateStatus(IDLE);
  }

  public void setServing(String name) {
    updateStatus(SERVING.formatted(name));
  }

  private void updateStatus(String text) {
    SwingUtilities.invokeLater(() -> statusLabel.setText(text));
  }

  public void runProgressBar(int duration) {
    int interval = 300;
    int steps = duration / interval;
    Timer timer = new Timer(interval, null);
    timer.addActionListener(e -> {
      int value = progressBar.getValue() + 100 / steps;
      if (value >= 100) {
        progressBar.setValue(0);
        timer.stop();
      } else {
        progressBar.setValue(value);
      }
    });
    timer.start();
  }
}
