import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EditableBufferedReader extends BufferedReader {

    //Constants
    private static final int RIGHT = 67;
    private static final int LEFT = 68;
    private static final int HOME = 72;
    private static final int END = 70;
    private static final int DEL = 51;
    private static final int INS = 50;

    InputStreamReader inputStreamReader;
    
    public EditableBufferedReader(InputStreamReader inputStreamReader) {

        super(inputStreamReader);
        this.inputStreamReader = inputStreamReader;

    } 
     
    public static void setRaw() { // put terminal in raw mode
        try {
            Runtime.getRuntime().exec(new String[] { "/bin/sh", "-c", "stty raw -echo </dev/tty" });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void unsetRaw() { // restore terminal to cooked mode
        try {
            Runtime.getRuntime().exec(new String[] { "/bin/sh", "-c", "stty echo cooked </dev/tty" });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int read() throws IOException{
        int key;
         if((key = super.read()) == 27) {     // filtra les tecles que volem que començen amb ESC (27)         
            super.read();                   // elimina el [
            switch(super.read()) {
                case RIGHT: return -RIGHT;
                case LEFT:  return -LEFT;
                case DEL:   return -DEL;
                case HOME:  return -HOME;
                case END: super.read();     //elimina el ~
                            return -END;
                case INS: super.read();
                            return -INS;                
            }
         }
         return key;
    }



    
}