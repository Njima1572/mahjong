public class Hai{

  private int number;
  private String type;

  public Hai(int _number, String _type){
    number = _number;
    type = _type;
  }

  public String getType(){
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
