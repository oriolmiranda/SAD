import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Server implements Runnable {

  public static Map<String, MySocket> clientsMap = new HashMap<String, MySocket>(); // diccionari de parells
                                                                                    // (nick,socket)
  private static final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
  private static final Lock r = rwl.readLock(); // lock de lectura
  private static final Lock w = rwl.writeLock(); // lock d'escriptura

  public MySocket mySocket;
  // public static boolean validUser = false; // boolean per si nom d'usuari
  // introduit es valid
  public String nick;

  public Server(String nickName, MySocket mySocket) {
    this.mySocket = mySocket;
    this.nick = nickName;
  }

  public static void main(String[] args) {
    MyServerSocket server = null;
    MySocket clientSocket;
    String name;

    try {
      server = new MyServerSocket(5000);

      while (true) {
        clientSocket = server.accept();
        if (clientsMap.size() >= 2) {
          System.out.println("La sala està plena, espera una estona i torna a intentar-ho");

        } else {
          if (clientsMap.containsKey("Jugador1")) {
            name = "Jugador2";
          } else {
            name = "Jugador1";
          }
          putClient(name, clientSocket);
          new Thread(new Server(name, clientSocket)).start();
          System.out.println(name + " s'ha unit");
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      server.close();
    }
  }

  @Override
  public void run() {
    sendOthers(nick);
    closeClient(nick);
    removeClient(nick);
  }

  // public static boolean usedNickName(String name) {
  //   boolean used;
  //   r.lock();
  //   try {
  //     used = clientsMap.containsKey(name); // comprovem que el nick no estigui ja en el diccionari
  //   } finally {
  //     r.unlock();
  //   }
  //   return used;
  // }

  public static void putClient(String name, MySocket clientSocket) {
    w.lock();
    try {
      clientsMap.put(name, clientSocket); // afegim el nou socket al diccionari amb un nom valid
    } finally {
      w.unlock();
    }
  }

  public static void closeClient(String nickName) {
    r.lock();
    try {
      clientsMap.get(nickName).close(); // tanquem el socket
    } finally {
      r.unlock();
    }
  }

  public static void removeClient(String nickName) {
    w.lock();
    try {
      clientsMap.remove(nickName); // borrem el socket del diccionari
    } finally {
      w.unlock();
    }
  }

  public static void sendOthers(String nickName) {
    int num;
    while ((num = clientsMap.get(nickName).readNum()) > 0) {
      r.lock();
      try {
        for (HashMap.Entry<String, MySocket> entry : clientsMap.entrySet()) { // per tots els usuaris del xat
          if (!entry.getKey().equals(nickName)) {
            entry.getValue().printNum(num);
          }
        }
      } finally {
        r.unlock();
      }
    }
    System.out.println("........ " + nickName + " ha sortit de la partida ........");
  }
}