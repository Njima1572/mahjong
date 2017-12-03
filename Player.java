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
    Scanner s = new Scanner(System.in);
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
    for(int i = 0; i < tehai.length - 1; i++){
      if(tehai[i] == justDiscarded){
        if(tehai[i] == tehai[i + 1]){
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
        if(tehai[i].getType() == justDiscarded.getType()){
          discardedIsBigger = isH1Bigger(justDiscarded, tehai[i]);
          if(!discardedIsBigger){
            if(tehai[i].getNumber() - justDiscarded.getNumber() == 1){
              for(int j = i + 1; j < tehai.length; j++){
                if(tehai[i] != tehai[j]){
                  if(tehai[i].getNumber() + 1 == tehai[j].getNumber()){
                    return true;
                  }
                }
              }
            }
          }else{
            if(justDiscarded.getNumber() - tehai[i].getNumber() == 1){
              for(int j = i + 1; j < tehai.length; j++){
                if(tehai[j] != tehai[i]){
                  if(tehai[i].getNumber() + 2 == tehai[j].getNumber()){
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
    for(int i = 0; i < tehai.length - 1; i++){
      if(tehai[i] == justDiscarded){
        if(tehai[i] == tehai[i + 1]){
          if(tehai[i] == tehai[i + 2]){
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

  public boolean isH1Bigger(Hai h1, Hai h2){
    return (h1.getNumber() > h2.getNumber());
  }

}
