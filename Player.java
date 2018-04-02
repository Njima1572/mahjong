import java.util.*;
public class Player{
  public char jikaze;
  Hai[] tehai = new Hai[14];
  Table t;
  Hai[] sutehai = new Hai[30];
  int shanten;
  boolean turnCheck;
  int point;

  Hai.Type SOUZU = Hai.Type.SOUZU;
  Hai.Type MANZU = Hai.Type.MANZU;
  Hai.Type PINZU = Hai.Type.PINZU;
  Hai.Type SANGEN = Hai.Type.SANGEN;
  Hai.Type KAZE = Hai.Type.KAZE;

  final int DEFAULT_POINT = 25000;

  /**
   * A constructor for a Player.
   * @param table where the player belongs to.
   */
  public Player(Table table)
  {
    t = table;
    point = DEFAULT_POINT; //can be flexible later.
  }

  /**
   * Gets a tile from mountain and discards a tile.
   */
  public void tsumo()
  {
    tehai[14]=t.mountain.pop();
    dahai();
  }

  /**
   * Discarding a tile. Asks for the index of tehai.
   */
  private void dahai()
  {
    Scanner s = new Scanner(System.in);//Input should be index number of tehai
    System.out.print("Enter an index of the tile to discard: ");
    for(int i = 0; i < sutehai.length; i++)
    {
      if(sutehai[i] == null)
      {
        sutehai[i] = tehai[s.nextInt()];
        t.justDiscarded = sutehai[i];
        break;
      }
    }

    tehai[s.nextInt()] = null;
    s.close();
    riipai();
  }

  /**
   * Organizes the hand by order
   */
  public void riipai()
  {
    ArrayList<ArrayList<Hai>> splitTehai = new ArrayList<ArrayList<Hai>>(); //in the order of souzu, manzu, pinzu, kaze, sangen
    for(int i=0; i<5; i++)
    {
      splitTehai.add(new ArrayList<Hai>());
    }

    for(int i=0; i<13; i++)
    {
      switch(tehai[i].getType())
      {
        case MANZU:
          splitTehai.get(0).add(tehai[i]);
          break;
        case PINZU:
          splitTehai.get(1).add(tehai[i]);
          break;
        case SOUZU:
          splitTehai.get(2).add(tehai[i]);
          break;
        case KAZE:
          splitTehai.get(3).add(tehai[i]);
          break;
        case SANGEN:
          splitTehai.get(4).add(tehai[i]);
          break;
      }
    }

    for(int i=0; i<5; i++)
    {
      boolean isSorted=false;
      while(!isSorted)
      {
        isSorted=true;
        for(int j=1; j<splitTehai.get(i).size(); j++)
        {
          if(splitTehai.get(i).get(j).getNumber()<splitTehai.get(i).get(j-1).getNumber())
          {
            Hai temp=splitTehai.get(i).get(j);
            splitTehai.get(i).set(j,splitTehai.get(i).get(j-1));
            splitTehai.get(i).set(j-1,temp);
            isSorted=false;
          }
        }
      }
    }
    int tehaiIndexCount=0;
    for(int i=0; i<5; i++)
    {
      for(int j=0; j<splitTehai.get(i).size(); j++)
      {
        tehai[tehaiIndexCount+j]=splitTehai.get(i).get(j);

      }
      tehaiIndexCount=tehaiIndexCount+splitTehai.get(i).size();
    }

  } 

  public int getShanten(){
    return shanten;
  }


  /**
   * Does all the moves to be done by a player.
   * @param checks for the turn.
   */
  public void turnMove(boolean turnCheck){
    tsumo();
    kanCheck(tehai[14]);
    dahai();
    t.turn ++;
  }

  /**
   * Checks if calling a tile is possible, if yes, then do the action.
   * @param justDiscarded a tile that was just discarded and be able to call.
   */
  public void naki(Hai justDiscarded){

    if(chiCheck(justDiscarded)){
      //Give an option for Chi
    		System.out.println("Do you want to Chi?: ");
    }
    if(ponCheck(justDiscarded)){
      //Give an option for Pon
    		System.out.println("Do you want to Pon?: ");
    }
    if(kanCheck(justDiscarded)){
      //Give an option for kan
    		System.out.println("Do you want to Kan?: ");
    }
    // if(ronCheck(justDiscarded)){
    //   //Give an option for Ron
    // }
  }
  
  /**
   * Checks if a player can Pon.
   * @return true if yes, false if no.
   * @param justDiscarded a tile that was just discarded to the table.
   */
  public boolean ponCheck(Hai justDiscarded){
    for(int i = 0; i < tehai.length - 2; i++){
      if(tehai[i].getType().equals(justDiscarded.getType()) && tehai[i].getNumber() == justDiscarded.getNumber()){
        if(tehai[i + 1].getType().equals(justDiscarded.getType()) && tehai[i + 1].getNumber() == justDiscarded.getNumber()){
          return true;
        }
      }
    }
    return false;
  }
  
  /**
   * This can be fixed with HaiCollection - Taatsu class
   * Checks if a player can Chi.
   * @return true if yes, false if no.
   * @param justDiscarded a tile that was just discarded to the table
   */
  public boolean chiCheck(Hai justDiscarded){
    boolean discardedIsBigger;
    boolean isDiscardedFromAPersonBefore;
    boolean isJihai;
    isJihai = (justDiscarded.getType() == KAZE || justDiscarded.getType() == SANGEN);
    if(!isJihai){
      for(int i = 0; i < tehai.length - 1; i++){
        if(tehai[i].getType().equals(justDiscarded.getType())){
          discardedIsBigger = isFirstTileBigger(justDiscarded, tehai[i]);
          if(!discardedIsBigger){                                      //if discarded is smaller ;  4 was discarded checking 5
            if(tehai[i].getNumber() - justDiscarded.getNumber() == 1){ //check if any tile is 1 value bigger.
              for(int j = i + 1; j < tehai.length - 1; j++){
                if(tehai[i].getType().equals(tehai[j].getType()) && tehai[i].getNumber() != tehai[j].getNumber()){ //if any tile is having same type but different value
                  if(tehai[i].getNumber() + 1 == tehai[j].getNumber()){ //check if there is any tile that is one value bigger than i eg. 56 - 4
                    return true;
                  }
                  if(tehai[i].getNumber() - 2 == tehai[j].getNumber()){//check if there is any tile that is two value smaller than i eg. 35 - 4
                    return true;
                  }
                }
              }
            }
          }else{
            if(justDiscarded.getNumber() - tehai[i].getNumber() == 1){ //if there is any tile that is a value smaller than discarded.
              for(int j = i + 1; j < tehai.length - 1; j++){
                if(tehai[j].getType().equals(tehai[i].getType()) && tehai[j].getNumber() != tehai[i].getNumber()){ //checking with same type but different value.
                  if(justDiscarded.getNumber() + 1 == tehai[j].getNumber()){ //if there is any that is two values smaller than discarded. eg. 23 - 4
                    return true;
                  }
                }
              }
            }
            if(justDiscarded.getNumber() - tehai[i].getNumber() == 2){ //if there is any tile that is two values smaller than just discarded;
              for(int j = 1; j < tehai.length - 1; j++){
                if(tehai[i].getType().equals(tehai[j].getType()) && tehai[i].getNumber() != tehai[j].getNumber()){// checking with same type but different value.
                  if(justDiscarded.getNumber() - 1 == tehai[j].getNumber()){ //if there is any that is a value smaller than discarded. eg. 23 - 4  seems like its same as the one before.
                    return true;
                  }
                }
              }
            }
          }
        }
      }
    }
    return false;
  }

  /**
   * Might be better to separate Ankan, Minkan, and Kakan
   * Checks if player can do kan
   * @param justDiscarded a tile that was just discarded to the table or Tsumoed Hai
   * @return true if yes, false if no.
   */
  public boolean kanCheck(Hai justDiscarded){
    for(int i = 0; i < tehai.length - 3; i++){
      if(tehai[i].getType().equals(justDiscarded.getType()) && tehai[i].getNumber() == justDiscarded.getNumber()){
        if(tehai[i + 1].getType().equals(justDiscarded.getType()) && tehai[i + 1].getNumber() == justDiscarded.getNumber()){
          if(tehai[i + 2].getType().equals(justDiscarded.getType()) && tehai[i + 2].getNumber() == justDiscarded.getNumber()){
            return true;
          }
        }
      }
    }
    return false;
  }

  // public boolean ronCheck(Hai justDiscarded){
  //   if(checkTenpai){// need a boolean value checkTenpai
  //
  //   }
  // }

  // public void chiAction(){
  //   if(chiCheck(t.justDiscarded)){
  //     for(int i = 0; i < tehai.length; i++){
  //       if(t.justDiscarded.getType() == tehai[i].getType()){
  //         if(t.justDiscarded.getType())
  //       }
  //     }
  //     dahai();
  //   }
  // }

  /**
   * Still need some improvement.
   * Will do the pon action. Takes two of a same tiles and a discarded tile
   * @param justDiscarded 
   */
 public void ponAction(Hai justDiscarded){
   if(ponCheck(justDiscarded)){
     Scanner s = new Scanner(System.in);
     System.out.print("Do you want to Pon? (y/n): ");
     String scanned = s.nextLine();
     s.close();
     int counter = 0;
     int idx = 0;

     if(scanned.equals("y")){
       while(counter < 2 && idx < 14){
         if(justDiscarded.equals(tehai[idx])){
           tehai[idx].nakiDone();
           counter++;
         }
         idx++;
       }
       justDiscarded.nakiDone();
       System.out.println("Pon completed!");
       dahai();
     }else{
       return;
     }
   }
 }
 /**
  * 
  */
  public void kanAction(Hai fourthTile){
	  if(kanCheck(forthTile)) {
		  Scanner s = new Scanner(System.in);
		  System.out.println("Do you want to Kan? (y/n ");
		  String scanned = s.nextLine();
		  s.close();
		  
	  }

  }


  public boolean isFirstTileBigger(Hai h1, Hai h2){
    return (h1.getNumber() > h2.getNumber());
  }

}
