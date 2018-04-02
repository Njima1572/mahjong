public class Hai implements Comparable<Hai> {

  public static enum Type{
	    MANZU("Manzu"),
	    PINZU("Pinzu"),
	    SOUZU("Souzu"),
	    KAZE("Kaze"),
	    SANGEN("Sangen");

	    private final String text;

	    private Type(final String text){
	      this.text = text;
	    }
	    public String toString(){
	      return this.text;
	  	}
	  }


  private int number;
  private Type type;
  private boolean is_naki;


  /**
   * Constructor for Hai
   * @param _number a number for a hai
   * @param _type a type of hai
   */
  public Hai(int _number, Type _type){
    number = _number;
    type = _type;
    is_naki = false;
  }

  /**
   * getter for Naki;
   * @return checks if hai was called or not
   */
  public boolean isNaki(){
    return is_naki;
  }
  /**
   * sets is_naki to true;
   */
  public void nakiDone(){
    is_naki = true;
  }


  /**
   * WARNING this does NOT compare the Type of tiles, only numbers.
   * @return positive integer if number of this > other, 0 if this = other, negative if this > other
   */
  public int compareTo(Hai other){
    if(this.getType().equals(other.getType())){
      return this.getNumber() - other.getNumber();
    }
    throw new IllegalArgumentException("Type of hais mismatch.");
  }

  /**
   * Getter for Type.
   * @return the type of the Hai.
   */
  public Type getType(){
    return type;
  }

  /**
   * Getter for Number.
   * @return the number of the Hai.
   */
  public int getNumber(){
    return number;
  }

  /**
   *
   * @return string representation of this Hai.
   */
  public String toString(){
    String num = Integer.toString(number);
    return num + type.toString();
  }
}
