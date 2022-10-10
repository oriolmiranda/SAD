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

  }
  public void left(){

  }
  public void insert(){

  }
  public void delete(){

  }
  public void backSpace(){

  }

  @Override
  public String toString(){

  return null;
  }
}