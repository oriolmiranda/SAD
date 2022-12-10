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

    public boolean isBarco(){ return barco; }
    public boolean isGirada(){ return girada; }
    public void setGirada(boolean b){
        if(girada = b){
            if(barco)
                setBackground(Color.BLUE);
            else
                setBackground(Color.LIGHT_GRAY);
        }else
            setBackground(new JButton().getBackground());
    }
    public void setBarco(boolean b){barco = b;}
}