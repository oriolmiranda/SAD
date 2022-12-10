import java.net.Socket;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;

public class MySocket extends Socket {

  Socket socket;
  BufferedReader buffReader;
  PrintWriter printWriter;

  public MySocket(Socket socket) {
    try {
      this.socket = socket;
      buffReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public MySocket(String host, int port) {
    try {
      socket = new Socket(host, port);
      buffReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public int readNum() {
    int num=0;
    try{
      String text = buffReader.readLine();
      num = Integer.parseInt(text);
    } catch (IOException e){
      e.printStackTrace();
    }
    return num;
  }

  public void printNum(int num){
    printWriter.println(num);
    printWriter.flush();
  }

  public void close() {
    try {
      buffReader.close();
      printWriter.close();
      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}