import java.util.*;
public class Player{
  public char jikaze;
  Hai[] tehai = new Hai[14];
  Table t;
  Hai[] sutehai = new Hai[30];
  int shanten;
  boolean turnCheck;
  int point;

  public Player(Table table)
  {
    t=table;
    point = 25000; //can be flexible later.
  }
  public void tsumo()
  {
    tehai[14]=t.mountain.pop();
    dahai();
  }
  private void dahai()
  {
    Scanner s = new Scanner(System.in);//Input should be index number of tehai
    for(int i = 0; i < sutehai.length; i++)
    {
      if(sutehai[i] == null)
      {
        sutehai[i] = tehai[s.nextInt()];
        t.justDiscarded = sutehai[i];
        break;
      }
    }

    tehai[s.nextInt()]=null;
    s.close();
    riipai();
  }

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
        case "souzu":
          splitTehai.get(0).add(tehai[i]);
          break;
        case "manzu":
          splitTehai.get(1).add(tehai[i]);
          break;
        case "pinzu":
          splitTehai.get(2).add(tehai[i]);
          break;
        case "kaze":
          splitTehai.get(3).add(tehai[i]);
          break;
        case "sangen":
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

  } //riipai()

  public int getShanten(){
    return shanten;
  }



  public void turnMove(boolean turnCheck){
    tsumo();
    kanCheck(t.justDiscarded);
    dahai();
    t.turn ++;
  }

  public void naki(Hai justDiscarded){

    if(chiCheck(justDiscarded)){
      //Give an option for Chi
    }
    if(ponCheck(justDiscarded)){
      //Give an option for Pon
    }
    if(kanCheck(justDiscarded)){
      //Give an option for kan
    }
    // if(ronCheck(justDiscarded)){
    //   //Give an option for Ron
    // }

  }
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

  public boolean chiCheck(Hai justDiscarded){
    boolean discardedIsBigger;
    boolean isDiscardedFromAPersonBefore;
    boolean isJihai;
    isJihai = (justDiscarded.getType() == "kaze" || justDiscarded.getType() == "sangen");
    if(!isJihai){
      for(int i = 0; i < tehai.length - 1; i++){
        if(tehai[i].getType().equals(justDiscarded.getType())){
          discardedIsBigger = isH1Bigger(justDiscarded, tehai[i]);
          if(!discardedIsBigger){                                      //if discarded is smaller 4 was discarded checking 5
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
  //       if(t.justDiscarded.getNumber() - tehai[i])
  //     }
  //     dahai();
  //   }
  // }

  public void ponAction(){
    if(ponCheck(t.justDiscarded)){
      Scanner s = new Scanner(System.in);
      String scanned = s.nextLine();
      int counter = 0;
      int i = 0;
      if(scanned == "y"){
        while(counter < 2){
          if(t.justDiscarded.equals(tehai[i])){
            tehai[i].nakiDone();
            counter++;
          }
          i++;
        }
        t.justDiscarded.nakiDone();
        dahai();
      }else{
        return;
      }
    }
  }

  public void kanAction(){

  }


  public boolean isH1Bigger(Hai h1, Hai h2){
    return (h1.getNumber() > h2.getNumber());
  }

}
