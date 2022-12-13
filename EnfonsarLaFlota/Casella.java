import javax.swing.*;
import java.awt.*;


public class Casella extends JButton {
    private boolean girada, barco;

    public Casella(String text) {
        super();
        this.setText(text);
        barco = false;
        girada = false;
    }

    public boolean isBarco() {
        return barco;
    }

    public boolean isGirada() {
        return girada;
    }

    // public void setGirada(boolean b) {
    //     if (girada = b) {
    //         if (barco) {
    //             setBackground(Color.DARK_GRAY);
    //             try {
    //                 playSound("sonidos/bip.wav");
    //             } catch (Exception e) {
    //                 e.printStackTrace();
    //             }
    //         } else {
    //             setBackground(Color.LIGHT_GRAY);
    //             try {
    //                 playSound("sonidos/water1.wav");
    //             } catch (Exception e) {
    //                 e.printStackTrace();
    //             }
    //         }
    //     } else
    //         setBackground(new JButton().getBackground());
    // }

    public void setGirada(boolean b) {
        if (girada = b) {
            if (barco) {
                setBackground(Color.DARK_GRAY);
            } else {
                setBackground(Color.LIGHT_GRAY);
            }
            this.setEnabled(false);
        } else
            setBackground(new JButton().getBackground());
    }

    public void setBarco(boolean b) {
        barco = b;
    }

    // public static void playSound(String fileName)
    //         throws MalformedURLException, LineUnavailableException, UnsupportedAudioFileException, IOException {
    //     File url = new File(fileName);
    //     Clip clip = AudioSystem.getClip();
    //     AudioInputStream ais = AudioSystem.getAudioInputStream(url);
    //     clip.open(ais);
    //     clip.start();
    // }
}