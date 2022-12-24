import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.*;
import java.io.File;
import java.net.MalformedURLException;

public class Client {

  public static final int PORT = 5000;
  private static final int FINALPARTIDA = 1;
  private static final int TORNRIVAL = 2;

  private static JocFrame jocFrame;
  private static MySocket mySocket;

  public static void main(String[] args) throws IOException {
    mySocket = new MySocket("localhost", PORT);
    jocFrame = new JocFrame();

    initializeButtons();

    new Thread(() -> { // Thread per llegir del servidor
      int output;
      while ((output = mySocket.readNum()) >= 0) {
        System.out.println(output);
        if (output == TORNRIVAL) { // desbloquejar jugador
          bloquejarJugador(false);
        } else if (output == FINALPARTIDA) { // mostrar que ha perdut
          bloquejarJugador(true);
          jocFrame.getFooterPanel().getTextArea().setText("Has perdut!");
          jocFrame.getFooterPanel().getTextArea().setBackground(Color.RED);
          jocFrame.getFooterPanel().setBackground(Color.RED);
          ImageIcon image = new ImageIcon("imatges/game-over.png");
          JOptionPane.showMessageDialog(jocFrame, null, "Derrota", 1, image);
        }
      }
    }).start();
  }

  public static void initializeButtons() { // Crea els actionListeners per a cada boto de la graella
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        final int fila = i, columna = j;
        jocFrame.getTaulell().getCasella(i, j).addActionListener(
            new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent actionEvent) {
                jocFrame.getTaulell().getCasella(fila, columna).setGirada(true);

                if (jocFrame.getTaulell().getCasella(fila, columna).isBarco()) {
                  if (fiPartida()) {
                    bloquejarJugador(true);
                    mySocket.printNum(FINALPARTIDA);
                    jocFrame.getFooterPanel().getTextArea().setText("Has guanyat!");
                    jocFrame.getFooterPanel().getTextArea().setBackground(Color.GREEN);
                    jocFrame.getFooterPanel().setBackground(Color.GREEN);
                    ImageIcon image = new ImageIcon("imatges/winner.png");
                    JOptionPane.showMessageDialog(jocFrame, null, "Victoria", 1, image);
                  } else {
                    try {
                      playSound("sons/bip.wav");
                    } catch (Exception e) {
                      e.printStackTrace();
                    }
                    fillFullBarco(fila, columna);
                  }
                } else {
                  try {
                    playSound("sons/water1.wav");
                  } catch (Exception e) {
                    e.printStackTrace();
                  }
                  bloquejarJugador(true);
                  mySocket.printNum(TORNRIVAL);
                }
              }
            });
      }
    }
  }

  public static void bloquejarJugador(Boolean bloqueo) { // True per bloquejar jugador i false per desbloquejar jugador
    Taulell taulell = jocFrame.getTaulell();
    Color green = Color.GREEN;
    Color red = Color.RED;
    if (!bloqueo) {
      jocFrame.getFooterPanel().getTextArea().setText("Es el teu torn");
      jocFrame.getFooterPanel().getTextArea().setBackground(green);
      jocFrame.getFooterPanel().setBackground(green);
    } else {
      jocFrame.getFooterPanel().getTextArea().setText("No es el teu torn");
      jocFrame.getFooterPanel().getTextArea().setBackground(red);
      jocFrame.getFooterPanel().setBackground(red);
    }

    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        if (!taulell.getCasella(i, j).isGirada())
          taulell.getCasella(i, j).setEnabled(!bloqueo);
      }
    }
  }

  public static Boolean fiPartida() { // Comproba si estan tots els vaixells tocats i enfonsats per si es final de partida
    Taulell taulell = jocFrame.getTaulell();
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        if (taulell.getCasella(i, j).isBarco() && !taulell.getCasella(i, j).isGirada()) {
          return false;
        }
      }
    }
    return true;
  }

  public static void fillFullBarco(int x, int y) {
    if (doneNext(x + 1, y, 1, 0) ||
        doneNext(x - 1, y, -1, 0) ||
        doneNext(x, y + 1, 0, 1) ||
        doneNext(x, y - 1, 0, -1))
      return;
    fillNext(x, y, 100, 100);
    fillNext(x + 1, y, 1, 0);
    fillNext(x - 1, y, -1, 0);
    fillNext(x, y + 1, 0, 1);
    fillNext(x, y - 1, 0, -1);
  }

  public static boolean doneNext(int x, int y, int dx, int dy) {
    if (x < 0 || x > 9 || y < 0 || y > 9)
      return false;
    if (jocFrame.getTaulell().getCasella(x, y).isBarco() && !jocFrame.getTaulell().getCasella(x, y).isGirada())
      return true;
    else if (jocFrame.getTaulell().getCasella(x, y).isBarco())
      return doneNext(x + dx, y + dy, dx, dy);
    else
      return false;
  }

  public static void fillNext(int x, int y, int dx, int dy) {
    if (x < 0 || x > 9 || y < 0 || y > 9)
      return;

    if (jocFrame.getTaulell().getCasella(x, y).isBarco()) {
      fillNext(x + dx, y + dy, dx, dy);

      if (x > 0 && y > 0)
        jocFrame.getTaulell().getCasella(x - 1, y - 1).setGirada(true);
      if (y > 0)
        jocFrame.getTaulell().getCasella(x, y - 1).setGirada(true);
      if (x < 9 && y > 0)
        jocFrame.getTaulell().getCasella(x + 1, y - 1).setGirada(true);
      if (x > 0)
        jocFrame.getTaulell().getCasella(x - 1, y).setGirada(true);
      if (x < 9)
        jocFrame.getTaulell().getCasella(x + 1, y).setGirada(true);
      if (x > 0 && y < 9)
        jocFrame.getTaulell().getCasella(x - 1, y + 1).setGirada(true);
      if (y < 9)
        jocFrame.getTaulell().getCasella(x, y + 1).setGirada(true);
      if (x < 9 && y < 9)
        jocFrame.getTaulell().getCasella(x + 1, y + 1).setGirada(true);
    }
  }

  public static void playSound(String fileName)
      throws MalformedURLException, LineUnavailableException, UnsupportedAudioFileException, IOException {
    File url = new File(fileName);
    Clip clip = AudioSystem.getClip();
    AudioInputStream ais = AudioSystem.getAudioInputStream(url);
    clip.open(ais);
    clip.start();
  }

}