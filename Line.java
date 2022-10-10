import java.util.Vector;

public class Line {
  
  boolean insert;
  Vector<Character> line;
  int position;

  public Line (){
    this.line = new Vector<>();
    this.insert = false;
    this.position = 0;
  }

  public void addCharacter(char character){
    if(insert & position < line.size()) {
      line.set(position, character);
    }
    else {
      line.add(position, character);
    }
    position++;
  }

  public void home(){

  }
  public void end(){

  }

  public void right(){
    if (position < line.size()) position ++;
  }

  public void left(){
    if (position > 0) position--;
  }

  public void insert(){
    insert = !insert;
  }

  public void delete(){

  }

  public void backSpace(){

  }

  @Override
  public String toString(){
    String str = "";
    for (char s : line) str += s;
    return str;
  }
}