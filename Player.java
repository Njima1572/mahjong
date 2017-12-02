import java.util.*;
public class Player{
  public char jikaze;
  Hai[] tehai = new Hai[14];
  Table t;
  Hai[] sutehai = new Hai[30];
  int shanten;
  public Player(Table table)
  {
    t=table;
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

    shanten = shantenCheck(splitTehai);

  } //riipai()

  public int getShanten(){
    return shanten;
  }

  public int shantenCheck(ArrayList<ArrayList<Hai>> splitTehai){
    int atama = 0;
    int kootsu = 0;
    int taatsu = 0;
    int shuntsu = 0;
    int sum;
    for(int i = 0; i < 5; i++){
      for(int j = 0; j < splitTehai.get(i).size() - 1; j++){
        if(splitTehai.get(i).size() > 2 && splitTehai.get(i).get(j).getNumber() == splitTehai.get(i).get(j + 1).getNumber()){
          if(splitTehai.get(i).get(j).getNumber() == splitTehai.get(i).get(j + 2).getNumber()){
            splitTehai.get(i).remove(j);
            splitTehai.get(i).remove(j + 1);
            splitTehai.get(i).remove(j + 2);
            kootsu += 1;
            j += 2;
            System.out.println("Kotsu found!");
            break;
          }
          splitTehai.get(i).remove(j);
          splitTehai.get(i).remove(j + 1);
          j += 1;
          if(atama < 1){
            System.out.println("Atama found!");
            atama += 1;
          }else{
            System.out.println("Tatsu found!");

            taatsu += 1;
          }
        }else if(i < 3 && splitTehai.get(i).size() > 2 && splitTehai.get(i).get(j).getNumber() + 1 == splitTehai.get(i).get(j + 1).getNumber()){
          if(splitTehai.get(i).size() > 2 && splitTehai.get(i).get(j).getNumber() + 2 == splitTehai.get(i).get(j + 2).getNumber()){
            splitTehai.get(i).remove(j);
            splitTehai.get(i).remove(j + 1);
            splitTehai.get(i).remove(j + 2);
            System.out.println("Shuntsu found!");

            shuntsu += 1;
            j += 2;
          }
        }else if(i < 3 && splitTehai.get(i).size() > 1 && splitTehai.get(i).get(j).getNumber() + 2 == splitTehai.get(i).get(j + 1).getNumber()){
          splitTehai.get(i).remove(j);
          splitTehai.get(i).remove(j + 1);
          System.out.println("Tatsu found!");

          taatsu += 1;
        }
      }
    }
    sum = 8 - (atama + kootsu * 2 + taatsu + shuntsu * 2);
    return sum;
  }

  public void naki()
  {

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
    for(int i = 0; i < tehai.length - 1; i++){
      if(tehai[i])
      discardedIsBigger = isH1Bigger(justDiscarded, tehai[i]);
      if(!discardedIsBigger){
        if(tehai[i].getNumber() - justDiscarded.getNumber() == 1){

        }


      }
    }

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

  public boolean ronCheck(Hai justDiscarded){
    if(checkTenpai){// need a boolean value checkTenpai

    }
  }

  public boolean isH1Bigger(Hai h1, Hai h2){
    return (h1.getNumber() > h2.getNumber());
  }

}
