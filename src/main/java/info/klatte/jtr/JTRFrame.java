package info.klatte.jtr;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.Toolkit;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class JTRFrame extends JFrame {
  Robot rbt = null;
  
  JTRFrame me = this;
  
  private JLabel jLabel1;
  
  public JTRFrame() {
    initComponents();
    try {
      this.rbt = new Robot();
    } catch (AWTException ex) {
      ex.printStackTrace();
      this.jLabel1.setText("Robot init failed!!");
    } 
    (new Jitterer()).start();
  }
  
  class Jitterer extends Thread {
    public void run() {
      while (true) {
        try {
          sleep(10000L);
        } catch (InterruptedException ex) {
          System.out.println("Jitterer thread interrupted, exiting...");
          System.exit(0);
        } 
        EventQueue.invokeLater(new Runnable() {
              public void run() {
                JTRFrame.this.me.jitter();
              }
            });
      } 
    }
  }
  
  private void jitter() {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    PointerInfo pi = MouseInfo.getPointerInfo();
    Point p = pi.getLocation();
    boolean plusX = true;
    boolean plusY = false;
    boolean minusY = false;
    if (screenSize.width <= p.x + 1) {
      plusX = false;
      plusY = true;
    } 
    if (screenSize.height <= p.y + 1) {
      plusY = false;
      minusY = true;
    } 
    if (plusX) {
      this.rbt.mouseMove(p.x + 1, p.y);
    } else if (plusY) {
      this.rbt.mouseMove(p.x, p.y + 1);
    } else if (minusY) {
      this.rbt.mouseMove(p.x, p.y - 1);
    } 
  }
  
  private void initComponents() {
    this.jLabel1 = new JLabel();
    setDefaultCloseOperation(3);
    setTitle("JTR");
    this.jLabel1.setForeground(new Color(255, 0, 0));
    this.jLabel1.setText("JTR is active!");
    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel1).addContainerGap(-1, 32767)));
    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel1).addContainerGap(-1, 32767)));
    pack();
  }
  
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
          public void run() {
            JTRFrame frame = new JTRFrame();
            frame.pack();
            Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration());
            Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
            int width = frame.getWidth();
            int height = frame.getHeight();
            int x = size.width - width - insets.right;
            int y = size.height - height - insets.bottom;
            frame.setLocation(x, y);
            frame.setVisible(true);
          }
        });
  }
}

