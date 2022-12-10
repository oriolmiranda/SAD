import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.*;

public class JocFrame extends JFrame{

  private HeaderPanel header;
  private Taulell taulell;
  private FooterPanel footer;

  public JocFrame(){

    this.setTitle("Enfonsar la flota");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setMinimumSize(new Dimension(600,600));
    this.setPreferredSize(new Dimension(700,700));
    this.setLayout(new BorderLayout());

    ImageIcon image = new ImageIcon("vaixell.png");
    this.setIconImage(image.getImage());

    header = new HeaderPanel();
    taulell = new Taulell();
    taulell.distribution();
    footer = new FooterPanel();

    this.add(header, BorderLayout.NORTH);
    this.add(taulell, BorderLayout.CENTER);
    this.add(footer, BorderLayout.SOUTH);

    this.setVisible(true);
  }

  public Taulell getTaulell(){
    return taulell;
  }

  public HeaderPanel getHeaderPanel(){
    return header;
  }

  public FooterPanel getFooterPanel(){
    return footer;
  }

  public static void main(String[] args) {
    new JocFrame();
  }
}