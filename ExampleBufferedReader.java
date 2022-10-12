import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class ExampleBufferedReader extends BufferedReader {
    
    //Constants
    private static final int RIGHT = 'C';
    private static final int LEFT = 'D';
    private static final int HOME = 'H';
    private static final int END = 'F';
    private static final int DEL = '3';
    private static final int INS = '2';

    private static final int BKSP = 127;

    InputStreamReader inputStreamReader;
    
    public ExampleBufferedReader(InputStreamReader inputStreamReader) {

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
         if((key = super.read()) == 27 && super.read() == '[') {     // filtra les tecles que volem que començen amb ESC (27)         
            switch(key = super.read()) {
                case END:    
                case INS: super.read(); //elimina el ~
                case RIGHT: 
                case LEFT:  
                case DEL:   
                case HOME: return -key;       
            }
         }
         return key;
    }

    @Override
    public String readLine() throws IOException{
        try{
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
                    default: line.addCharacter((char)key);
                }
            }
            return line.toString();
        } catch (IOException e){
            throw e;
        } finally {
            unsetRaw();
        }
    }
      
    
    public static void main(String[] args){
        
       BufferedReader bufRead = new ExampleBufferedReader(new InputStreamReader(System.in));
       
       try{      

       System.out.print("\033[@");

       
       System.out.print(bufRead.readLine());

       } catch (IOException ioe){
           ioe.printStackTrace();
       }    
    }    
}