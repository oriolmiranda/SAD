import java.awt.*;
import java.util.Random;

import javax.swing.*;

public class Taulell extends JPanel {

  private Casella[][] tablero;

  public Taulell() {

    this.setLayout(new GridLayout(10, 10, 5, 5));
    tablero = new Casella[10][10];

    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        tablero[i][j] = new Casella("");
        this.add(tablero[i][j]);
      }
    }
  }

  public Casella[][] getTablero() {
    return tablero;
  }

  public Casella getCasella(int x, int y) {
    return tablero[x][y];
  }

  public void distribution() {
    for (int i = 4; i > 0; i--) {
      for (int j = 5 - i; j > 0; j--) {
        while (tryDistribution(i)) {
        }
      }
    }
  }

  public boolean tryDistribution(int size) {
    Random r = new Random();
    int x, y;
    boolean direction = r.nextBoolean(); // true -> horitzontal, False -> vertical
    if (direction) {
      x = r.nextInt(10 - size + 1);
      y = r.nextInt(10);
    } else {
      x = r.nextInt(10);
      y = r.nextInt(10 - size + 1);
    }
    for (int i = 0; i < size; i++) {
      if (direction && checkSurroundings(x + i, y) ||
          !direction && checkSurroundings(x, y + i))
        return true;
    }
    for (int i = 0; i < size; i++) {
      if (direction)
        tablero[x + i][y].setBarco(true);
      else
        tablero[x][y + i].setBarco(true);
    }
    return false;
  }

  public boolean checkSurroundings(int x, int y) {
    if (x > 0 && y > 0 && tablero[x - 1][y - 1].isBarco() ||
        y > 0 && tablero[x][y - 1].isBarco() ||
        x < 9 && y > 0 && tablero[x + 1][y - 1].isBarco() ||
        x > 0 && tablero[x - 1][y].isBarco() ||
        tablero[x][y].isBarco() ||
        x < 9 && tablero[x + 1][y].isBarco() ||
        x > 0 && y < 9 && tablero[x - 1][y + 1].isBarco() ||
        y < 9 && tablero[x][y + 1].isBarco() ||
        x < 9 && y < 9 && tablero[x + 1][y + 1].isBarco())
      return true;
    return false;
  }
}