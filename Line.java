import java.util.ArrayList;

public class Line {
  
  boolean insert;
  ArrayList<Character> line;
  int position;

  public Line (){
    this.line = new ArrayList<>();
    this.insert = false;
    this.position = 0;
  }

  public void addCharacter(char character){
    if (!this.insert || this.position >= line.size() - 1) {
      line.set(this.position, character);
    }
    else {
      line.add(this.position, character);
    }
    position++;       
    if (!this.insert) {
      System.out.print("\033[@");
    }
    System.out.print(character);
  }


  public void home(){
    if(this.position > 0){
      System.out.print("\033[" + this.position + "D");
      this.position = 0;
    }      
  }

  public void end(){
    if(this.position < line.size()){
      System.out.print("\033[" + (line.size() - this.position) + "C");
      position = line.size();
    }
  }

  public void right(){
    if (this.position < line.size()){
      this.position++;
      System.out.print("\033[C");
    } 
  }

  public void left(){
    if (this.position > 0){
      this.position--;
      System.out.print("\033[D");
    } 
  }

  public void insert(){
    this.insert = !this.insert;
  }

  public void delete(){
    if(this.position < line.size())
    {
      line.remove(this.position);
      System.out.print("\033[P");
    }
  }

  public void backSpace(){
    if(this.position > 0)
    {
      this.position--;      
      System.out.print("\033[D");
      line.remove(this.position);         
      System.out.print("\033[P");
    }    
  }

  @Override
  public String toString(){
    String str = "";
    for (char s : line) 
      str += s;
    return str;
  }
}