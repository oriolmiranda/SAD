import java.util.Observer;
import java.util.Observable;

public class Console implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        switch(arg.toString()){
            case "\033[D":                          //Left
                System.out.print("\033[D");
            break;
            case "\033[C":                          //Right
                System.out.print("\033[C");
            break;
            case "\033[P":                          //Delete
                System.out.print("\033[P");
            break;
            default:
                System.out.print((char) arg);
            break;
        }
    }    
}
