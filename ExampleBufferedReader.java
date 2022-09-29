import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ExampleBufferedReader extends BufferedReader {

    InputStreamReader inputStreamReader;
    
    public ExampleBufferedReader(InputStreamReader inputStreamReader) {

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
    
    public static void main(String[] args){
        
       BufferedReader bufRead = new ExampleBufferedReader(new InputStreamReader(System.in));
       
       try{
       
       ExampleBufferedReader.setRaw();
       int i = 0;
       int c;
       while((c = bufRead.read()) != '\r'){
           System.out.println(c);
       }
       
       System.out.print("Hola hola");
       
       
       ExampleBufferedReader.unsetRaw();
       } catch (IOException ioe){
           ioe.printStackTrace();
       }
       
    }    
}