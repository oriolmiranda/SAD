import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.*;


public class EditableBufferedReader extends BufferedReader {
    
    //Constants
    private static final int RIGHT = 'C';
    private static final int LEFT = 'D';
    private static final int HOME = 'H';
    private static final int END = 'F';
    private static final int DEL = '3';
    private static final int INS = '2';

    private static final int BKSP = 127;

    InputStreamReader inputStreamReader;
    
    public EditableBufferedReader(InputStreamReader inputStreamReader) {

        super(inputStreamReader);
        this.inputStreamReader = inputStreamReader;

    } 
     
    public static void setRaw() { // put terminal in raw mode
        try {
            Runtime.getRuntime().exec(new String[] { "/bin/sh", "-c", "stty -echo raw </dev/tty" });
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

    @Override
    public String readLine() throws IOException{
        setRaw();
        Line line = new Line();
        int key;
        while ((key = this.read()) != '\r'){
            switch(key)
            {
                case -RIGHT: line.right();
                break;
                case -LEFT: line.left();
                break;
                case -HOME: line.home();
                break;
                case -END: line.end();
                break;
                case -INS: line.insert();
                break;
                case -DEL: line.delete();
                break;
                case BKSP: line.backSpace();
                break;
                default: line.addCharacter((char)key)
            }
        }
        unsetRaw();
        return line.toString();
    }
}    