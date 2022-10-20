import java.util.ArrayList;
import java.util.Observable;

public class Line extends Observable {
  
  boolean insert;
  ArrayList<Character> line;
  int position;
  Console view;

  public Line (){
    line = new ArrayList<>();
    insert = false;
    position = 0;
    view = new Console();
    this.addObserver(view);
  }

  public void addCharacter(char character){
    if (!insert || position >= line.size()) {
      line.add(position, character);      
      System.out.print("\033[@"); //no se si cal
    }
    else {
      line.set(position, character);
    }
    position++;
    this.setChanged();
    this.notifyObservers(character);    //System.out.print(character);    
  }

  public void home(){
    /*if(position > 0){
      System.out.print("\033[" + position + "D");
      position = 0;
    }*/
    while(position>0){
      this.left();
    }     
  }

  public void end(){
    /*if(position < line.size()){
      System.out.print("\033[" + (line.size() - position) + "C");
      position = line.size();
    }*/
    while(position<line.size()){
      this.right();
    }
  }

  public void right(){
    if (position < line.size()){
      position++;
      this.setChanged();
      this.notifyObservers("\033[C");   //System.out.print("\033[C");
    } 
  }

  public void left(){
    if (position > 0){
      position--;
      this.setChanged();
      this.notifyObservers("\033[D");   //System.out.print("\033[D");
    } 
  }

  public void insert(){    
    insert = !insert;
  }

  public void delete(){
    if(position < line.size())
    {
      line.remove(position);
      this.setChanged();
      this.notifyObservers("\033[P");     //System.out.print("\033[P");
    }
  }

  public void backSpace(){
    if(position > 0)
    {
      position--;      
      line.remove(position); 
      /*System.out.print("\033[D");              
      System.out.print("\033[P");*/
      this.left();
      this.delete();
    }    
  }

  @Override
  public String toString(){
    StringBuilder str = new StringBuilder();
    for (char s : line) 
      str = str.append(s);
    return str.toString();
  }
}