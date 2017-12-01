public class OTile{

  private int number;
  private char type;

  public OTile(int _number, char _type){
    number = _number;
    type = _type;
  }

  public char getType(){
    return type;
  }
  public int getNumber(){
    return number;
  }
  public String toString(){
    String num = Integer.toString(number);
    return num + type;
  }
}
